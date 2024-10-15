package com.bugbusters.swe_backend;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCase1 {
    /*
        Julie Luu is responsible for Test Case 1 which states: User select dates for stay. User sees rooms that are available then user quits.
        1. User can't book on the same day
        2. Check-out date cannot be before check-in date
        3. User can't book more than a year in advance
    */

    @Test
    void SameDayBooking() {
        // User can't book on the same day
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = LocalDate.now();
        
        boolean isValidBooking = isBookingValid(checkInDate, checkOutDate);
        
        // Since check-in and check-out are the same, the booking should be invalid
        assertFalse(isValidBooking, "Booking on the same day should not be allowed.");
    }

    @Test
    void CheckOutBeforeCheckIn() {
        // Check-out date can't be before check-in date
        LocalDate checkInDate = LocalDate.of(2024, 10, 20);
        LocalDate checkOutDate = LocalDate.of(2024, 10, 19);  // Check-out is before check-in
        
        boolean isValidBooking = isBookingValid(checkInDate, checkOutDate);
        
        // The booking should be invalid because check-out is before check-in
        assertFalse(isValidBooking, "Check-out date cannot be before check-in date.");
    }

    @Test
    void BookingMoreThanYearInAdvance() {
        // User can't book more than a year in advance
        LocalDate checkInDate = LocalDate.now().plusYears(1).plusDays(1);  // More than 1 year ahead
        LocalDate checkOutDate = checkInDate.plusDays(2);  // Check-out date is 2 days after check-in
        
        boolean isValidBooking = isBookingValid(checkInDate, checkOutDate);
        
        // The booking should be invalid because it is more than one year in advance
        assertFalse(isValidBooking, "Booking more than a year in advance should not be allowed.");
    }

    @Test
    void ValidBooking() {
        // Valid booking case: check-in is tomorrow, and check-out is 2 days after
        LocalDate checkInDate = LocalDate.now().plusDays(1);  // Tomorrow
        LocalDate checkOutDate = checkInDate.plusDays(2);  // 2 days later
        
        boolean isValidBooking = isBookingValid(checkInDate, checkOutDate);
        
        // This should be a valid booking
        assertTrue(isValidBooking, "Booking should be allowed with valid check-in and check-out dates.");
    }

    /*
     * This method checks if the booking is valid based on criteria
     */
    private boolean isBookingValid(LocalDate checkInDate, LocalDate checkOutDate) {
        LocalDate today = LocalDate.now();
        LocalDate oneYearFromNow = today.plusYears(1);
        
        // Rule 1: Check-out should be after check-in
        if (checkOutDate.isBefore(checkInDate)) {
            return false;
        }
        
        // Rule 2: No same-day booking
        if (checkInDate.equals(today)) {
            return false;
        }

        // Rule 3: No booking more than a year in advance
        if (checkInDate.isAfter(oneYearFromNow)) {
            return false;
        }
        
        return true;
    }
}
