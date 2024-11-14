package com.bugbusters.swe_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "Bookings")

// AC1019 -- This class is just mapping to the Booking table to represent a booking that a guest makes
// AC1019 -- Getters and Setters are handled by LomBok which I am still unsure how that works, but it does the job

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;

    /*
        AC1019 -- As shown in the ER Diagram, there is a many-to-one relationship between the guest and booking table. A guest can make multiple bookings at a time,
        but a booking is linked to one guest.
    */

    @ManyToOne
    @JoinColumn(name = "guestID", nullable = false)
    private Guest myGuest;

    /*
        AC1019 -- One room can have many bookings over time, but at any given moment, a room can only have one booking tied
        to it, i.e., one guest for a specific date range.
    */

    @ManyToOne
    @JoinColumn(name = "roomID", nullable = false)
    private Room myRoom;

    @Column(nullable = false)
    private LocalDateTime checkInTime;

    @Column(nullable = false)
    private LocalDateTime checkOutTime;


}
