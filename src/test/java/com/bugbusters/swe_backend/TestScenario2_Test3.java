package com.bugbusters.swe_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestScenario2_Test3 {

    /*
        AC1015 -- Worked on Test scenario #2 which states: "User selects hotel and date for stay. User selects room/rooms. User quits."
        This test if guest books too far in advance
    */

    @Test
    void guestSelectHotel() {
        String selectedHotel = "Test Hotel";
        assertEquals("Test Hotel", selectedHotel);
    }


    @Test
    void guestSelectInvalidDate() {
        /*
            AC1015 -- A user should not be able to book 1 years in advance
        */

        int year = 2026;
        int expectedYear = 2025;
        assertThat(year).isLessThanOrEqualTo(expectedYear);
    }
}
