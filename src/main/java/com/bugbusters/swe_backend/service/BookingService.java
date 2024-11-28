package com.bugbusters.swe_backend.service;

import com.bugbusters.swe_backend.dto.BookingDTO;
import com.bugbusters.swe_backend.dto.GuestDTO;
import com.bugbusters.swe_backend.dto.PaymentDTO;
import com.bugbusters.swe_backend.entity.*;
import com.bugbusters.swe_backend.exception.ResourceNotFoundException;
import com.bugbusters.swe_backend.exception.RoomUnavailableException;
import com.bugbusters.swe_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository myBookingRepository;

    @Autowired
    private GuestRepository myGuestRepository;

    @Autowired
    private RoomRepository myRoomRepository;

    @Autowired
    private PaymentRepository myPaymentRepository;

    @Autowired
    private PaymentService myPaymentService;

    @Autowired
    private ConfirmationRepository myConfirmationRepository;


    /*
        AC1127 -- This java file does a lot... For this createBooking method, it validates guest, room, payment, basically everything
        Note: This method also updates the room's availability, and also generates the confirmation number
    */

    @Transactional
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Guest guest;
        if (bookingDTO.getGuestID() != null) {
            guest = myGuestRepository.findById(bookingDTO.getGuestID())
                    .orElseThrow(() -> new ResourceNotFoundException("Guest not found with ID: " + bookingDTO.getGuestID()));
        } else if (bookingDTO.getMyGuest() != null) {
            guest = createNewGuest(bookingDTO.getMyGuest());
        } else {
            throw new IllegalArgumentException("Guest information is required to create a booking.");
        }

        Room room = myRoomRepository.findById(bookingDTO.getRoomID())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + bookingDTO.getRoomID()));

        if (!isRoomAvailable(room.getRoomID(), bookingDTO.getCheckInTime(), bookingDTO.getCheckOutTime())) {
            throw new RoomUnavailableException("Room is unavailable for the selected dates.");
        }

        Payment payment = null;
        if (bookingDTO.getMyPayment() != null) {
            PaymentDTO paymentDTO = bookingDTO.getMyPayment();
            payment = new Payment();
            payment.setGuest(guest);
            payment.setCardNumber(paymentDTO.getCardNumber());
            payment.setCvv(paymentDTO.getCvv());
            payment.setExpDate(paymentDTO.getExpDate());
            payment.setFirstName(paymentDTO.getFirstName());
            payment.setLastName(paymentDTO.getLastName());
            myPaymentRepository.save(payment);
        }

        // AC1127 -- So after payment and guest stuff is validated, the booking is made, room availability is set to false
        room.setAvailability(false);
        myRoomRepository.save(room);

        Booking booking = new Booking();
        booking.setMyGuest(guest);
        booking.setMyRoom(room);
        booking.setCheckInTime(bookingDTO.getCheckInTime());
        booking.setCheckOutTime(bookingDTO.getCheckOutTime());
        Booking savedBooking = myBookingRepository.save(booking);

        // AC1127 -- and once booking is saved, the confirmation number is generated through another method
        String confNumber = generateConfirmationNumber();
        Confirmation confirmation = new Confirmation();
        confirmation.setBookingID(savedBooking.getBookingID());
        confirmation.setConfNum(confNumber);
        myConfirmationRepository.save(confirmation);

        BookingDTO responseDTO = new BookingDTO();
        responseDTO.setGuestID(guest.getGuestID());
        responseDTO.setMyGuest(mapGuestToDTO(guest));
        responseDTO.setRoomID(room.getRoomID());
        responseDTO.setCheckInTime(savedBooking.getCheckInTime());
        responseDTO.setCheckOutTime(savedBooking.getCheckOutTime());
        responseDTO.setConfirmationNumber(confNumber);

        return responseDTO;
    }



    //AC1127 -- This method actually generates the confirmation number once a booking is saved, it's a randomly generated 16 character string
    private String generateConfirmationNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }


    //AC1127 -- Ignore this method, still seeing if I can implement and update system using a confirmation number
    public Booking updateBooking(Long bookingID, BookingDTO bookingDTO) {
        Booking booking = myBookingRepository.findById(bookingID)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingID));

        if (bookingDTO.getRoomID() != null) {
            Room room = myRoomRepository.findById(bookingDTO.getRoomID())
                    .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + bookingDTO.getRoomID()));
            booking.setMyRoom(room);
        }

        if (bookingDTO.getCheckInTime() != null) {
            booking.setCheckInTime(bookingDTO.getCheckInTime());
        }
        if (bookingDTO.getCheckOutTime() != null) {
            booking.setCheckOutTime(bookingDTO.getCheckOutTime());
        }

        return myBookingRepository.save(booking);
    }


    /*
        AC1127 -- straight forward method. It cancels a booking using confirmation number and updates the room's availability.
    */

    @Transactional
    public void cancelBooking(String confirmationNumber) {
        // Step 1: Find confirmation by confirmation number
        Confirmation confirmation = myConfirmationRepository.findByConfNum(confirmationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Confirmation not found for number: " + confirmationNumber));

        Booking booking = myBookingRepository.findById(confirmation.getBookingID())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found for confirmation ID: " + confirmation.getBookingID()));

        Room room = booking.getMyRoom();
        if (room != null) {
            room.setAvailability(true);
            myRoomRepository.save(room);
        }

        myBookingRepository.delete(booking);
        myConfirmationRepository.delete(confirmation);
    }


    // AC1127 -- This method retrieves all rooms available for booking within a given date range and room type thas the front-end sends
    public List<Room> findAvailableRooms(LocalDateTime checkIn, LocalDateTime checkOut, String roomType) {
        return myRoomRepository.findAvailableRooms(checkIn, checkOut, roomType);
    }

    // ignore this, working on something
    public boolean isRoomAvailable(Long roomID, LocalDateTime checkIn, LocalDateTime checkOut) {
        return myBookingRepository.findBookings(roomID, checkIn, checkOut).isEmpty();
    }


    private Guest createNewGuest(GuestDTO guestDTO) {
        if (guestDTO == null) {
            throw new IllegalArgumentException("Guest information is required to create a new guest.");
        }

        Guest guest = new Guest();
        guest.setFName(guestDTO.getFname());
        guest.setLName(guestDTO.getLname());
        guest.setEmail(guestDTO.getEmail());
        guest.setPhoneNumber(guestDTO.getPhoneNumber());
        return myGuestRepository.save(guest);
    }

    //AC1127 -- The next two methods just converts the entities into DTO objects
    private GuestDTO mapGuestToDTO(Guest guest) {
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setFname(guest.getFName());
        guestDTO.setLname(guest.getLName());
        guestDTO.setEmail(guest.getEmail());
        guestDTO.setPhoneNumber(guest.getPhoneNumber());
        return guestDTO;
    }


    private PaymentDTO mapPaymentToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setFirstName(payment.getFirstName());
        paymentDTO.setLastName(payment.getLastName());
        paymentDTO.setExpDate(payment.getExpDate());
        return paymentDTO;
    }


    /*
        AC1127 -- This method handles booking details that will be sent to frontend when teh guest wants to see their booking details
        room, gueest, and payment info is sent based on the confirmation number
    */

    @Transactional(readOnly = true)
    public BookingDTO getBookingDetails(String confNum) {
        Confirmation confirmation = myConfirmationRepository.findByConfNum(confNum)
                .orElseThrow(() -> new ResourceNotFoundException("Confirmation not found for number: " + confNum));

        Booking booking = myBookingRepository.findById(confirmation.getBookingID())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found for confirmation ID: " + confirmation.getBookingID()));

        Guest guest = booking.getMyGuest();
        Payment payment = myPaymentRepository.findByGuest_GuestID(guest.getGuestID())
                .orElse(null);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setGuestID(guest.getGuestID());
        bookingDTO.setMyGuest(mapGuestToDTO(guest));
        bookingDTO.setRoomID(booking.getMyRoom().getRoomID());
        bookingDTO.setCheckInTime(booking.getCheckInTime());
        bookingDTO.setCheckOutTime(booking.getCheckOutTime());
        bookingDTO.setMyPayment(payment != null ? mapPaymentToDTO(payment) : null);
        bookingDTO.setConfirmationNumber(confNum);

        return bookingDTO;
    }
}
