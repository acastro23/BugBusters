package com.bugbusters.swe_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "confirmation")
public class Confirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long confirmationID;

    @Column(nullable = false)
    private Long bookingID;

    @Column(nullable = false, unique = true)
    private String confNum;

    @OneToOne
    @JoinColumn(name = "bookingID", referencedColumnName = "bookingID", insertable = false, updatable = false)
    private Booking booking;
}
