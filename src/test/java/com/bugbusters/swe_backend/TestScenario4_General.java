package com.bugbusters.swe_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class TestScenario4_General  {
    
      /* Sumeya */

      private static Set<String> bookingDatabase = new HashSet<>();


      @Test
      void Uniquenum() {
          //  confirmation number is in the database
          String existingConfirmationNumber = "ABC123";
          BookingToDatabase(existingConfirmationNumber); // This adds the booking to the "database"
          // try to see if a new booking in a database is unique 
          String newConfirmationNumber = "ABC123";
          boolean isUnique = isConfirmationNumberUnique(newConfirmationNumber);
          // invaild is confimration already exists 
          assertFalse(isUnique, "Booking with a duplicate confirmation number should not be allowed.");
          removeBookingFromDatabase(existingConfirmationNumber); // remove booking 
      }
  
      @Test
      void DuplicateNumber() {
          String existingConfirmationNumber = "XYZ789";  //if booking confirmation already exists 
          BookingToDatabase(existingConfirmationNumber); // adding to booking
  
          // duplicate
          String duplicateConfirmationNumber = "XYZ789";
          boolean isDuplicate = !isConfirmationNumberUnique(duplicateConfirmationNumber); // should return true for duplicates
  
          
          assertTrue(isDuplicate, "Booking with a duplicate confirmation number should be detected."); // test should assert that it's a duplicate
  
          removeBookingFromDatabase(existingConfirmationNumber); // Clean up after the test
      }
  
      @Test
      void Lengthconfirm() {
          String validConfirmationNumber = "123456";  // a valid 6-digit confirmation number
          boolean isValidLength = isConfirmationNumberValidLength(validConfirmationNumber);
  
          //check if confirm num is vaild *6 digits long*
          assertTrue(isValidLength, "Confirmation number should be valid when it is 6 digits long.");
  
          // Simulate an invalid confirmation number *if its too short or too long*
          String invalidConfirmationNumber = "12345"; // only 5 digits
          boolean isInvalidLength = isConfirmationNumberValidLength(invalidConfirmationNumber);
  
          // Check if the confirmation number is invalid  *not 6 digits long*
          assertFalse(isInvalidLength, "Confirmation number should be invalid if it is not exactly 6 digits long.");
  
      }
  
       private void BookingToDatabase(String confirmationNumber) { // adding a booking confirmation number to the database
          bookingDatabase.add(confirmationNumber);
      }
  
      private void removeBookingFromDatabase(String confirmationNumber) { // removing a booking confirmation number from the database
          bookingDatabase.remove(confirmationNumber);
      }
  
      private boolean isConfirmationNumberUnique(String confirmationNumber) { // Checks if the confirmation number is unique *not already in the database*
          return !bookingDatabase.contains(confirmationNumber);
      }
  
      private boolean isConfirmationNumberValidLength(String confirmationNumber) { // checks if the confirmation num is exactly 6 digits long
          return confirmationNumber.matches("\\d{6}");  // checks confirmation number is 6 digits
      }
  }