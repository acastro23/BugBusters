package com.bugbusters.swe_backend.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugbusters.swe_backend.entity.Booking;
import com.bugbusters.swe_backend.entity.Guest;
import com.bugbusters.swe_backend.entity.Room;
import com.bugbusters.swe_backend.exception.ResourceNotFoundException;
import com.bugbusters.swe_backend.repository.GuestRepository;
import com.bugbusters.swe_backend.repository.RoomRepository;
import com.bugbusters.swe_backend.service.BookingService;

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
    
    @PutMapping("/update")
public ResponseEntity<Booking> updateBooking(@RequestParam Long bookingID, 
                                             @RequestParam LocalDateTime checkIn, 
                                             @RequestParam LocalDateTime checkOut) {
    Booking updatedBooking = myBookingService.updateBooking(bookingID, checkIn, checkOut);
    return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

    @DeleteMapping("/cancel")
public ResponseEntity<String> cancelBooking(@RequestParam Long bookingID) {
    myBookingService.cancelBooking(bookingID);
    return new ResponseEntity<>("Booking canceled successfully", HttpStatus.OK);
    }
}
