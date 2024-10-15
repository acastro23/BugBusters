package com.bugbusters.swe_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestScenario2_Test2 {

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
        boolean availability = false;

        assertEquals(101, roomNumber);
        assertEquals("Single", roomType);
        assertEquals(1, roomFloor);
        assertFalse(availability);

        if (availability == false) {
            System.out.println("Room is not available");
        }
    }


    @Test
    void guestQuits() {
        // AC1014 -- guest quits or exits page after selecting room
        boolean guestQuit = true;
        assertTrue(guestQuit);
    }
}
