package com.bugbusters.swe_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment")     // maps to our payment table in the database
@Getter
@Setter
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "cardnumber", nullable = false)
    private Long cardNumber;

    @Column(name = "cvv", nullable = false)
    private int cvv;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "expdate", nullable = false)
    private int expDate;
}
