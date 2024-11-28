package com.bugbusters.swe_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomID;

    @Column(name = "roomnumber", nullable = false)
    private int roomNumber;

    @Column(name = "floor", nullable = false)
    private int floor;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "availability", nullable = false)
    private boolean availability = true;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
