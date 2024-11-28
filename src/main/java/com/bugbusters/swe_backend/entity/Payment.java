package com.bugbusters.swe_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment")     // maps to our payment table in the database
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;                                         //AC1118 -- added the payment id

    @Column(name = "cardnumber", nullable = false)
    private String cardNumber;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    @Column(name = "expdate", nullable = false)
    private String expDate;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    //AC1118 -- All changes made by Alex start here
    @ManyToOne
    @JoinColumn(name = "guestid", nullable = false)
    private Guest guest;
}