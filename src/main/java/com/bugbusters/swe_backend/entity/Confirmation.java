package com.bugbusters.swe_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "confirmation")     // maps to our confirmation table in the database
@Getter
@Setter
public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    

    @Column(name = "confirmationid", nullable = false)
    private Integer confirmationID;

    //I know this is not right. I don't remeber how to do the creation if it is already created elsewhere
    @Column(name = "bookingid", nullable = false)
    private Integer bookingID;

    @Column(name = "confum", nullable = false)
    private String conFNum;
}
