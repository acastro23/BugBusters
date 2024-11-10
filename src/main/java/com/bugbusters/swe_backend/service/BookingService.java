package com.bugbusters.swe_backend.service;

import com.bugbusters.swe_backend.entity.Booking;
import com.bugbusters.swe_backend.entity.Guest;
import com.bugbusters.swe_backend.entity.Room;
import com.bugbusters.swe_backend.exception.RoomUnavailableException;
import com.bugbusters.swe_backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;

// AC1019 -- Service class handles the logic/rules for booking rooms
// AC1019 -- The rules set are validating room availability, checking the check-in and check-out time(making sure its valid)
// AC1019 -- The logic for creating the booking is also stored in this class

@Service
public class BookingService {

    @Autowired
    private BookingRepository myBookingRepository;


    public boolean isRoomAvailable(Long roomID, LocalDateTime checkIn, LocalDateTime checkOut) {
        List<Booking> myBookings = myBookingRepository.findBookings(roomID, checkIn, checkOut);
        return myBookings.isEmpty();
    }


    public Booking createBooking(Guest myGuest, Room myRoom, LocalDateTime checkIn, LocalDateTime checkOut) {

        // AC1019 -- First if statement will check if the room is available. Second if statement checks to check in and out date
        //AC1019 -- The exceptions thrown are were created and stored in the exception package

        if (!isRoomAvailable(myRoom.getRoomID(), checkIn, checkOut)) {
            throw new RoomUnavailableException("Room is unavailable for the selected date");
        }

        if (checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("Check-out date cannot be before check-in date");
        }

        // AC1019 -- If all is validated, then create the booking for that guest and save in the database
        Booking myBooking = new Booking();
        myBooking.setMyGuest(myGuest);
        myBooking.setMyRoom(myRoom);
        myBooking.setCheckInTime(checkIn);
        myBooking.setCheckOutTime(checkOut);

        return myBookingRepository.save(myBooking);
    }
}
