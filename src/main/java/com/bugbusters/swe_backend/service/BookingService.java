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
    private ConfirmationRepository myConfirmationRepository;

    @Autowired
    private GuestService myGuestService;


    /*
        AC1127 -- This java file does a lot... For this createBooking method, it validates guest, room, payment, basically everything
        Note: This method also updates the room's availability, and also generates the confirmation number
    */

    @Transactional
    public BookingDTO createBooking(BookingDTO myBookingDTO) {

        Guest myGuest = (myBookingDTO.getGuestID() != null)
                ? myGuestRepository.findById(myBookingDTO.getGuestID())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with ID: " + myBookingDTO.getGuestID()))
                : myGuestService.createOrReuseGuest(myBookingDTO.getMyGuest());

        Room myRoom = myRoomRepository.findById(myBookingDTO.getRoomID())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + myBookingDTO.getRoomID()));

        if (!isRoomAvailable(myRoom.getRoomID(), myBookingDTO.getCheckInTime(), myBookingDTO.getCheckOutTime())) {
            throw new RoomUnavailableException("That Room is unavailable for the chosen dates. Please try another Room.");
        //Changes from Nathan
        } else if (myBookingDTO.getCheckInTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Check-in time cannot be in the past.");
        } else if (!myBookingDTO.getCheckOutTime().isAfter(myBookingDTO.getCheckInTime())) {
            throw new IllegalArgumentException("Check-out time must be after check-in time.");
        }

        Payment myPayment = null;
        if (myBookingDTO.getMyPayment() != null) {
            PaymentDTO myPaymentDTO = myBookingDTO.getMyPayment();
            myPayment = new Payment();
            myPayment.setGuest(myGuest);
            myPayment.setCardNumber(myPaymentDTO.getCardNumber());
            myPayment.setCvv(myPaymentDTO.getCvv());
            myPayment.setExpDate(myPaymentDTO.getExpDate());
            myPayment.setFirstName(myPaymentDTO.getFirstName());
            myPayment.setLastName(myPaymentDTO.getLastName());
            myPaymentRepository.save(myPayment);
        }

        Booking myBooking = new Booking();
        myBooking.setMyGuest(myGuest);
        myBooking.setMyRoom(myRoom);
        myBooking.setCheckInTime(myBookingDTO.getCheckInTime());
        myBooking.setCheckOutTime(myBookingDTO.getCheckOutTime());
        Booking savedBooking = myBookingRepository.save(myBooking);

        String confNumber = makeConfNum();
        Confirmation confirmation = new Confirmation();
        confirmation.setBookingID(savedBooking.getBookingID());
        confirmation.setConfNum(confNumber);
        myConfirmationRepository.save(confirmation);

        BookingDTO responseDTO = new BookingDTO();
        responseDTO.setGuestID(myGuest.getGuestID());
        responseDTO.setMyGuest(mapGuestToDTO(myGuest));
        responseDTO.setRoomID(myRoom.getRoomID());
        responseDTO.setCheckInTime(savedBooking.getCheckInTime());
        responseDTO.setCheckOutTime(savedBooking.getCheckOutTime());
        responseDTO.setConfirmationNumber(confNumber);

        return responseDTO;
    }


    //AC1127 -- This method actually generates the confirmation number once a booking is saved, it's a randomly generated 16 character string
    private String makeConfNum() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }


    //AC1127 -- Ignore this method, still seeing if I can implement and update system using a confirmation number
    public Booking updateBooking(Long bookingID, BookingDTO bookingDTO) {
        Booking myBooking = myBookingRepository.findById(bookingID)
                .orElseThrow(() -> new ResourceNotFoundException("There was no booking found with ID: " + bookingID));

        if (bookingDTO.getRoomID() != null) {
            Room room = myRoomRepository.findById(bookingDTO.getRoomID())
                    .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + bookingDTO.getRoomID()));
            myBooking.setMyRoom(room);
        }

        if (bookingDTO.getCheckInTime() != null) {
            myBooking.setCheckInTime(bookingDTO.getCheckInTime());
        }
        if (bookingDTO.getCheckOutTime() != null) {
            myBooking.setCheckOutTime(bookingDTO.getCheckOutTime());
        }

        return myBookingRepository.save(myBooking);
    }


    /*
        AC1127 -- straight forward method. It cancels a booking using confirmation number and updates the room's availability.
    */

    @Transactional
    public void cancelBooking(String confirmationNumber) {
        // Step 1: Find confirmation by confirmation number
        Confirmation confirmation = myConfirmationRepository.findUniqueByConfNum(confirmationNumber)
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
            //Changes from Nathan
        } else if (!guestDTO.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        } else if (!guestDTO.getPhoneNumber().matches("^\\d{3}-\\d{3}-\\d{4}$")) {
            throw new IllegalArgumentException("Invalid phone number.");
        } else if (!guestDTO.getFname().matches("^[A-Za-z]+(?:[-' ][A-Za-z]+)*$") || !guestDTO.getLname().matches("^[A-Za-z]+(?:[-' ][A-Za-z]+)*$")){
            throw new IllegalArgumentException(("The name may only contains letters."));
        }

        Guest guest = new Guest();
        guest.setFName(guestDTO.getFname());
        guest.setLName(guestDTO.getLname());
        guest.setEmail(guestDTO.getEmail());
        guest.setPhoneNumber(guestDTO.getPhoneNumber());
        return myGuestRepository.save(guest);
    }

    //AC1127 -- The next two methods just converts the entities into DTO objects
    private GuestDTO mapGuestToDTO(Guest myGuest) {
        GuestDTO myGuestDTO = new GuestDTO();
        myGuestDTO.setFname(myGuest.getFName());
        myGuestDTO.setLname(myGuest.getLName());
        myGuestDTO.setEmail(myGuest.getEmail());
        myGuestDTO.setPhoneNumber(myGuest.getPhoneNumber());
        return myGuestDTO;
    }


    private PaymentDTO mapPaymentToDTO(Payment payment) {
        PaymentDTO myPaymentDTO = new PaymentDTO();
        myPaymentDTO.setFirstName(payment.getFirstName());
        myPaymentDTO.setLastName(payment.getLastName());
        myPaymentDTO.setExpDate(payment.getExpDate());
        return myPaymentDTO;
    }


    /*
        AC1127 -- This method handles booking details that will be sent to frontend when teh guest wants to see their booking details
        room, guest, and payment info is sent based on the confirmation number
    */

    @Transactional(readOnly = true)
    public BookingDTO getBookingDetails(String confNum) {
        Confirmation myConfirmation = myConfirmationRepository.findUniqueByConfNum(confNum)
                .orElseThrow(() -> new ResourceNotFoundException("Nothing found for the confirmation number: " + confNum));


        Booking myBooking = myBookingRepository.findById(myConfirmation.getBookingID())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found for myConfirmation ID: " + myConfirmation.getBookingID()));

        Guest myGuest = myBooking.getMyGuest();
        Payment payment = myPaymentRepository.findByGuest_GuestID(myGuest.getGuestID())
                .orElse(null);

        BookingDTO myBookingDTO = new BookingDTO();
        myBookingDTO.setGuestID(myGuest.getGuestID());
        myBookingDTO.setMyGuest(mapGuestToDTO(myGuest));
        myBookingDTO.setRoomID(myBooking.getMyRoom().getRoomID());
        myBookingDTO.setCheckInTime(myBooking.getCheckInTime());
        myBookingDTO.setCheckOutTime(myBooking.getCheckOutTime());
        myBookingDTO.setMyPayment(payment != null ? mapPaymentToDTO(payment) : null);
        myBookingDTO.setConfirmationNumber(confNum);

        return myBookingDTO;
    }
}
