package com.bugbusters.swe_backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestScenario2_Test2 {

    /*
        AC1015 -- Test Scenario 2: User Selects hotel and date for stay. User selects room/rooms. User quits.
        This test class will test a scenario where a user attempts to book more than five room
    */


    @Test
    void guestBooksRoom()
    {
        int numOfRooms = 6;
        assertThrows(IllegalArgumentException.class, () -> {
            bookRooms(numOfRooms);
        }, "Guest should not be able to book more than 5 rooms!");
    }


    // AC1015 -- When the guest tries to book more than five room, throw an exception
    void bookRooms(int numOfRooms) {
        if (numOfRooms > 5) {
           throw new IllegalArgumentException("You can not book more than 5 rooms!");
        }
    }

}
