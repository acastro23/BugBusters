package com.bugbusters.swe_backend.controller;

import com.bugbusters.swe_backend.entity.Booking;
import com.bugbusters.swe_backend.entity.Guest;
import com.bugbusters.swe_backend.entity.Room;
import com.bugbusters.swe_backend.exception.ResourceNotFoundException;
import com.bugbusters.swe_backend.service.BookingService;
import com.bugbusters.swe_backend.repository.GuestRepository;
import com.bugbusters.swe_backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

/*
    AC1019 -- BookingController does exactly what the other controller classes do, i.e., expose endpoints
    These have already been tested using PostMan, but here's a rundown

    When we make a booking, we need the guestID and roomID, so that's why we create instances of those respective classes here, and
    To create a booking we will take in a few parameters, the guestID, roomID, checkIn, and checkOut time.

    I am assuming that when a guest first goes into the hotel page they fill out a form with basic information that is needed for the guest table,
    then they go browse the available room, select date, and then request a booking.
*/

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService myBookingService;

    @Autowired
    private GuestRepository myGuestRepository;

    @Autowired
    private RoomRepository myRoomRepository;


    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestParam Long guestID, @RequestParam Long roomID,
                                                 @RequestParam LocalDateTime checkIn, @RequestParam LocalDateTime checkOut) {
        // AC1019 -- So here myGuest and myRoom are retrieving guest and room by their primary keys, and it will throw an error if it is not found.

        Guest myGuest = myGuestRepository.findById(guestID)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with ID: " + guestID));
        Room myRoom = myRoomRepository.findById(roomID)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomID));

        Booking myBooking = myBookingService.createBooking(myGuest, myRoom, checkIn, checkOut);

        return new ResponseEntity<>(myBooking, HttpStatus.CREATED);
    }
}
