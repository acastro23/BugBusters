package com.bugbusters.swe_backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PutMapping("/update/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable("id") Long bookingID,
                                                 @RequestParam LocalDateTime checkIn,
                                                 @RequestParam LocalDateTime checkOut) {

    /*
        AC1124 -- Updating the booking should happen for a specific booking ID, which is why we use "/update/{id}".
        Pass the bookingID and the updated check-in/out times to the service layer for processing.
    */

        Booking updatedBooking = myBookingService.updateBooking(bookingID, checkIn, checkOut);
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }


    //AC1124 -- Same thing applies to canceling a booking, specify the id in the endpoint
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        myBookingService.cancelBooking(id);
        return new ResponseEntity<>("Booking canceled successfully.", HttpStatus.OK);
    }


    /*
        AC1125 -- reminder to make use of this method later
    */

    @GetMapping("/check-availability")
    public ResponseEntity<Boolean> checkRoomAvailability(
            @RequestParam Long roomID,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkOut) {
        boolean isAvailable = myBookingService.isRoomAvailable(roomID, checkIn, checkOut);
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }


    /*
    AC1126 -- This endpoint is for the calendar feature, so the guest will start by selecting their check-in and check-out date, and also select their desired
    room type. The front-end sends all the required info to the back-end, and the back-end will return a list of rooms available that you can then display
    to the guest. Rooms that are booked for the selected dates are not shown
    */

    @GetMapping("/available-rooms")
    public ResponseEntity<List<Room>> getAvailableRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkOut,
            @RequestParam(required = false) String roomType) {
        List<Room> availableRooms = myBookingService.findAvailableRooms(checkIn, checkOut, roomType);
        return new ResponseEntity<>(availableRooms, HttpStatus.OK);
    }
}
