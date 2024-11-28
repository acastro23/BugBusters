package com.bugbusters.swe_backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.bugbusters.swe_backend.dto.BookingDTO;
import com.bugbusters.swe_backend.entity.Confirmation;
import com.bugbusters.swe_backend.repository.ConfirmationRepository;
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

    @Autowired
    private ConfirmationRepository myConfirmationRepository;


    /*
        AC1127 -- This endpoint handles creating a booking for guests. The frontend needs to send the guest's details and the roomID for the room the guest wants,
        and the check-in and check-out dates. The information is checked by the service class if valid, the booking is made.
    */


    @PostMapping("/create")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO myBookingDTO) {
        if (myBookingDTO.getRoomID() == null) {
            throw new IllegalArgumentException("Room ID can't be null.");
        }

        if (myBookingDTO.getGuestID() == null && myBookingDTO.getMyGuest() == null) {
            throw new IllegalArgumentException("Either guestID or guest details must be provided.");
        }

        BookingDTO bookingDTO = myBookingService.createBooking(myBookingDTO);
        return new ResponseEntity<>(bookingDTO, HttpStatus.CREATED);
    }


    //AC1127 -- I was working on an update functionaliy, ignore this.

    @PutMapping("/update/{id}")
    public ResponseEntity<Booking> updateBooking(
            @PathVariable("id") Long bookingID,
            @RequestBody BookingDTO bookingDTO) {
        Booking updatedBooking = myBookingService.updateBooking(bookingID, bookingDTO);
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }


    //AC1127 -- Where the guest enters their confirmation number to view booking details, you can have an option to cancel booking on that same page and it calls
    // this enpoint which cancels the booking

    @DeleteMapping("/cancel/{confirmationNumber}")
    public ResponseEntity<String> cancelBooking(@PathVariable String confirmationNumber) {
        myBookingService.cancelBooking(confirmationNumber);
        return ResponseEntity.ok("Booking canceled successfully. Sorry for any inconvenience");
    }



    /*
        AC1125 -- check room availability, probably delete, no longer needed as of right now
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
    AC1126 -- This endpoint is for the calndar feature, so the guest will start by selecting their check-in and check-out date, and also select their desired
    room type. The front-end sends all the required info to the back-end, and the back-end will return a list of rooms available that you can then display
    to the guest. Rooms that are booked for the seleced dates are not shown
    */

    @GetMapping("/available-rooms")
    public ResponseEntity<List<Room>> getAvailableRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkOut,
            @RequestParam(required = false) String roomType) {
        List<Room> availableRooms = myBookingService.findAvailableRooms(checkIn, checkOut, roomType);
        return new ResponseEntity<>(availableRooms, HttpStatus.OK);
    }


    /*
        AC1127 -- In order to show booking details to the guest when they enter a confirmation number, fetch this endpoint.
    */

    @GetMapping("/details")
    public ResponseEntity<BookingDTO> getBookingDetails(@RequestParam String confNum) {
        BookingDTO bookingDetails = myBookingService.getBookingDetails(confNum);
        return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
    }

}
