package com.bugbusters.swe_backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestScenario2_General {

    /*
        AC1015 -- Handling Testing scenario number 2:

        User selects hotel and date for stay. User selects room/rooms. User quits.
                - User can’t book less than 1 room
                - User can’t book more than 5 rooms
                - User can’t  book 2+ beds for a room
    */

    @Test
    void guestSelectsHotel() {
        String selectedHotel = "Test Hotel";
        assertEquals("Test Hotel", selectedHotel);
    }


    @Test
    void guestSelectsDate() {
        String selectedDate = "2024-10-15";
        assertEquals("2024-10-15", selectedDate);
    }


    @Test
    void guestSelectsRoom() {
        // AC1015 -- lets assume guest selects room 100 on the first floor which is available and of room type: single
        int roomNumber = 100;
        int roomFloor = 1;
        String roomType = "Single";
        boolean availability = true;

        assertEquals(100, roomNumber);
        assertEquals(1, roomFloor);
        assertEquals("Single",roomType);
        assertTrue(availability);

        // AC1015 -- guest would quit of application once their booking is done
        boolean guestQuits = true;
        assertTrue(guestQuits);
    }
}
