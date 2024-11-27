package com.bugbusters.swe_backend.service;

import com.bugbusters.swe_backend.entity.Booking;
import com.bugbusters.swe_backend.entity.Guest;
import com.bugbusters.swe_backend.entity.Room;
import com.bugbusters.swe_backend.exception.ResourceNotFoundException;
import com.bugbusters.swe_backend.exception.RoomUnavailableException;
import com.bugbusters.swe_backend.repository.BookingRepository;
import com.bugbusters.swe_backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository myBookingRepository;

    @Autowired
    private RoomRepository myRoomRepository;


    public boolean isRoomAvailable(Long roomID, LocalDateTime checkIn, LocalDateTime checkOut) {
        List<Booking> myBookings = myBookingRepository.findBookings(roomID, checkIn, checkOut);
        return myBookings.isEmpty();
    }


    public Booking createBooking(Guest myGuest, Room myRoom, LocalDateTime checkIn, LocalDateTime checkOut) {
        validateBookingDetails(myGuest, myRoom, checkIn, checkOut);

        if (!isRoomAvailable(myRoom.getRoomID(), checkIn, checkOut)) {
            throw new RoomUnavailableException("Room is unavailable for the selected date.");
        }

        Booking myBooking = new Booking();
        myBooking.setMyGuest(myGuest);
        myBooking.setMyRoom(myRoom);
        myBooking.setCheckInTime(checkIn);
        myBooking.setCheckOutTime(checkOut);
        myRoom.setAvailability(false);
        myRoomRepository.save(myRoom);

        return myBookingRepository.save(myBooking);
    }


    public Booking updateBooking(Long bookingID, LocalDateTime checkIn, LocalDateTime checkOut) {
        Booking existingBooking = myBookingRepository.findById(bookingID)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with ID " + bookingID + " not found"));

        validateDates(checkIn, checkOut);

        List<Booking> conflictingBookings = myBookingRepository.findBookingsForUpdate(
                existingBooking.getMyRoom().getRoomID(), checkIn, checkOut, bookingID);

        if (!conflictingBookings.isEmpty()) {
            throw new RoomUnavailableException("Room is unavailable for the updated date range");
        }

        existingBooking.setCheckInTime(checkIn);
        existingBooking.setCheckOutTime(checkOut);

        return myBookingRepository.save(existingBooking);
    }




    public void cancelBooking(Long bookingID) {
        Booking existingBooking = myBookingRepository.findById(bookingID)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with the given ID does not exist."));

        // Update room availability
        Room myRoom = existingBooking.getMyRoom();
        myRoom.setAvailability(true);
        myRoomRepository.save(myRoom);

        myBookingRepository.deleteById(existingBooking.getBookingID());
    }


    private void validateBookingDetails(Guest myGuest, Room myRoom, LocalDateTime checkIn, LocalDateTime checkOut) {
        if (myGuest == null) {
            throw new IllegalArgumentException("Guest information is required.");
        }

        if (myRoom == null) {
            throw new IllegalArgumentException("Room information is required.");
        }

        validateDates(checkIn, checkOut);
    }


    private void validateDates(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Check-in and check-out times are required.");
        }

        if (checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("Check-out date cannot be before the check-in date.");
        }

        if (checkIn.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past.");
        }
    }

    // AC1126 -- This is for the available-room endpoint, it just gets all the available rooms for the date range and room type.
    public List<Room> findAvailableRooms(LocalDateTime checkIn, LocalDateTime checkOut, String roomType) {
        List<Room> allRooms = roomType == null
                ? myRoomRepository.findAll()
                : myRoomRepository.findByType(roomType);

        return allRooms.stream()
                .filter(room -> isRoomAvailable(room.getRoomID(), checkIn, checkOut))
                .collect(Collectors.toList());
    }

}
