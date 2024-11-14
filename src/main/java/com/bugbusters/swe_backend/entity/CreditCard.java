package com.bugbusters.swe_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment")     // maps to our payment table in the database
@Getter
@Setter
//Test Comment
//Test Comment
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "cardnumber", nullable = false)
    private String cardNumber;

    @Column(name = "cvv", nullable = false)
    private int cvv;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "expdate", nullable = false)
    private int expdate;
}
