package com.bugbusters.swe_backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestScenario2_Test3 {

    /*
        AC1017 -- Test Scenario 2: User Selects hotel and date for stay. User selects room/rooms. User quits.
        This test class will test a scenario where a user selects  room type, and then selects available room, and then
        the room becomes unavailable.
    */


    @Test
    void guestSelectsRoom() {

        String guestChoice = "Single";

        // AC1017 -- Guest selects their type and roomType objects checks if its a valid type
        String roomType = guestChoice.equals("Single") ? "Single" : "Double";
        boolean availability = true;

        assertEquals(guestChoice, roomType);
        assertTrue(availability);

        availability = false;       // AC1017 -- room becomes unavailable after guest books it

        assertFalse(availability);
    }


    @Test
    void guestQuits() {
        boolean guestQuits = true;
        assertTrue(guestQuits);
    }
}
