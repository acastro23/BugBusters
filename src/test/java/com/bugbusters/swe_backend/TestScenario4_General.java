package com.bugbusters.swe_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

@SpringBootTest
public class TestCase4 {

/* Sumeya */

 @Test
    void Uniquenum() {
        // Checks if it is the only confimation number in the database, to insure it is unique 

        // Simulate an existing booking with a confirmation number in the database
        String existingConfirmationNumber = "ABC123";
        addBookingToDatabase(existingConfirmationNumber); // This would be a mock function to add the booking
    
        // Attempt to create a new booking with the same confirmation number
        String newConfirmationNumber = "ABC123";
        boolean isUnique = isConfirmationNumberUnique(newConfirmationNumber);
    
        // Since the confirmation number already exists, the new booking should be invalid
        assertFalse(isUnique, "Booking with a duplicate confirmation number should not be allowed.");
        
        // Clean up or remove the booking after the test (if necessary)
        removeBookingFromDatabase(existingConfirmationNumber);
    }
    

    @Test
    void DuplicateNumber() {
        // Checks if the confirmation number given is a duplicate  
      
 // Simulate an existing booking with a confirmation number in the database
      String existingConfirmationNumber = "XYZ789";
      addBookingToDatabase(existingConfirmationNumber); // Mock function to add the booking

 // Attempt to create a new booking with the same confirmation number (duplicate)
       String duplicateConfirmationNumber = "XYZ789";
       boolean isDuplicate = !isConfirmationNumberUnique(duplicateConfirmationNumber); // Should return true for duplicates

 // Since the confirmation number is already used, the test should assert that it's a duplicate
       assertTrue(isDuplicate, "Booking with a duplicate confirmation number should be detected.");

 // Clean up after the test (if necessary)
       removeBookingFromDatabase(existingConfirmationNumber);

    }

    @Test
    void Lengthconfirm() {
        // Checks if the confirmation number is 6 digit long, the confirmation number has to be 6 digit for all customers 
      // Simulate a valid 6-digit confirmation number
    String validConfirmationNumber = "123456";
    boolean isValidLength = isConfirmationNumberValidLength(validConfirmationNumber);

    // Check if the confirmation number is valid (i.e., 6 digits long)
    assertTrue(isValidLength, "Confirmation number should be valid when it is 6 digits long.");

    // Simulate an invalid confirmation number (e.g., too short or too long)
    String invalidConfirmationNumber = "12345"; // Only 5 digits
    boolean isInvalidLength = isConfirmationNumberValidLength(invalidConfirmationNumber);

    // Check if the confirmation number is invalid (i.e., not 6 digits long)
    assertFalse(isInvalidLength, "Confirmation number should be invalid if it is not exactly 6 digits long.");
    }
}
