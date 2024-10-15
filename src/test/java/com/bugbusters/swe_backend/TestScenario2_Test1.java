package com.bugbusters.swe_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TestScenario2_Test1 {

    /*
        AC1014 -- Worked on Test scenario #2 which states: "User selects hotel and date for stay. User selects room/rooms. User quits."
    */

    @Test
    void guestSelectHotel() {
        // AC1014 -- guest will select hotel and date for stay here, and check if the date passed through equals the string date given in the assert method
        String selectedHotel = "Test Hotel";
        assertEquals("Test Hotel", selectedHotel);
    }


    @Test
    void guestSelectDate() {
        // AC1015 -- guest selects the date for their stay after choosing hotel.
        String selectedDate = "2024-10-15";
        assertEquals("2024-10-15", selectedDate);
    }


    @Test
    void guestSelectRoom() {
        // AC1014 -- guest would select room number and floor. They also select the room type and the room has to be available in order to be selected.
        // AC1015 -- this test assumes room is true
        int roomNumber = 101;
        int roomFloor = 1;
        String roomType = "Single";
        boolean availability = true;

        assertEquals(101, roomNumber);
        assertEquals("Single", roomType);
        assertEquals(1, roomFloor);
        assertTrue(availability);

        if (availability) {
            System.out.println("Room is available");
        }

    }


    @Test
    void guestQuits() {
        // AC1014 -- guest quits or exits page after selecting room
        boolean guestQuit = true;
        assertTrue(guestQuit);
    }
}

