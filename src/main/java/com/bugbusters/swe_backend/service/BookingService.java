package com.bugbusters.swe_backend.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugbusters.swe_backend.entity.Booking;
import com.bugbusters.swe_backend.entity.Guest;
import com.bugbusters.swe_backend.entity.Room;
import com.bugbusters.swe_backend.exception.RoomUnavailableException;
import com.bugbusters.swe_backend.repository.BookingRepository;

// AC1019 -- Service class handles the logic/rules for booking rooms
// AC1019 -- The rules set are validating room availability, checking the check-in and check-out time (making sure it's valid)
// AC1019 -- The logic for creating, updating, and canceling bookings is stored in this class

@Service
public class BookingService {

    @Autowired
    private BookingRepository myBookingRepository;

    /**
     * Checks if a room is available for the given date range.
     *
     * @param roomID  The ID of the room to check.
     * @param checkIn The start date and time of the booking.
     * @param checkOut The end date and time of the booking.
     * @return True if the room is available, false otherwise.
     */
    public boolean isRoomAvailable(Long roomID, LocalDateTime checkIn, LocalDateTime checkOut) {
        List<Booking> myBookings = myBookingRepository.findBookings(roomID, checkIn, checkOut);
        return myBookings.isEmpty();
    }

    /**
     * Creates a booking if the room is available and the input is valid.
     *
     * @param myGuest  The guest making the booking.
     * @param myRoom   The room to be booked.
     * @param checkIn  The check-in date and time.
     * @param checkOut The check-out date and time.
     * @return The created Booking object.
     */
    public Booking createBooking(Guest myGuest, Room myRoom, LocalDateTime checkIn, LocalDateTime checkOut) {
        if (myGuest == null) {
            throw new IllegalArgumentException("Guest information is required");
        }

        if (myRoom == null) {
            throw new IllegalArgumentException("Room information is required");
        }

        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Check-in and check-out times are required");
        }

        if (!isRoomAvailable(myRoom.getRoomID(), checkIn, checkOut)) {
            throw new RoomUnavailableException("Room is unavailable for the selected date");
        }

        if (checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("Check-out date cannot be before check-in date");
        }

        if (checkIn.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }

        Booking myBooking = new Booking();
        myBooking.setMyGuest(myGuest);
        myBooking.setMyRoom(myRoom);
        myBooking.setCheckInTime(checkIn);
        myBooking.setCheckOutTime(checkOut);

        return myBookingRepository.save(myBooking);
    }

    /**
     * Updates an existing booking with new details.
     *
     * @param bookingID The ID of the booking to update.
     * @param checkIn   The updated check-in date and time.
     * @param checkOut  The updated check-out date and time.
     * @return The updated Booking object.
     */
    public Booking updateBooking(Long bookingID, LocalDateTime checkIn, LocalDateTime checkOut) {
        Optional<Booking> existingBookingOpt = myBookingRepository.findById(bookingID);

        if (existingBookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Booking with the given ID does not exist");
        }

        Booking existingBooking = existingBookingOpt.get();

        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Check-in and check-out times are required");
        }

        if (checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("Check-out date cannot be before check-in date");
        }

        if (checkIn.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }

        if (!isRoomAvailable(existingBooking.getMyRoom().getRoomID(), checkIn, checkOut)) {
            throw new RoomUnavailableException("Room is unavailable for the updated date range");
        }

        existingBooking.setCheckInTime(checkIn);
        existingBooking.setCheckOutTime(checkOut);

        return myBookingRepository.save(existingBooking);
    }

    /**
     * Cancels a booking by its ID.
     *
     * @param bookingID The ID of the booking to cancel.
     */
    public void cancelBooking(Long bookingID) {
        Optional<Booking> existingBookingOpt = myBookingRepository.findById(bookingID);

        if (existingBookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Booking with the given ID does not exist");
        }

        myBookingRepository.deleteById(bookingID);
    }
}
