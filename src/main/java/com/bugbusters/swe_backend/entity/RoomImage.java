package com.bugbusters.swe_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "roomimages")         //AC1103 -- Supabase lowercases everything for some reason, it's annoying
public class RoomImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageID;

    @ManyToOne
    @JoinColumn(name = "roomid", nullable = false)          // AC1103 -- roomImages has one foreign key
    private Room room;

    private String imageURL;

}
