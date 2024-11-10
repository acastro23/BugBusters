package com.bugbusters.swe_backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestScenario2_Test1 {

    /*
        AC1015 -- Test Scenario 2: User Selects hotel and date for stay. User selects room/rooms. User quits.
        This test class will test a scenario where a user attempts to book less than one room
    */

    @Test
    void guestBooksRoom()
    {
        /*
            AC1015 -- So if the guest selects to book less than 1 room then the exception gets thrown and the test passes.
                      However, if numOfRooms >= 1, the test should fail because no exception would get thrown.
        */
        int numOfRooms = 0;
        assertThrows(IllegalArgumentException.class, () -> {
            bookRooms(numOfRooms);}, "User should not be able to book less than one room");
    }


    // AC1015 -- When the guest tries to book less than one room, this method will throw an exception stating that booking < 1 room is not a valid selection
    void bookRooms(int numOfRooms) {
        if (numOfRooms < 1)
        {
            throw new IllegalArgumentException("You can not book less than one room");
        }
    }

}
