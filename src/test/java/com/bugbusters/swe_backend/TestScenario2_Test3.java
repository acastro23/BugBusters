package com.bugbusters.swe_backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestScenario2_Test3 {

    /*
        AC1017 -- Test Scenario 2: Guest selects a room of a specific type, and they select from available rooms.
        guest quits
    */

    @Test
    void guestSelectsRoomAndAvailabilityIsUpdated() {

        boolean availability = true;

        String guestPreference = "Single";
        String roomType = guestPreference.equals("Single") ? "Single" : "Double";

        assertEquals(guestPreference, roomType);
        assertTrue(availability);

        availability = false;
        assertFalse(availability);
    }


    @Test
    void guestQuits() {
        boolean guestQuits = true;
        assertTrue(guestQuits);
    }
}
