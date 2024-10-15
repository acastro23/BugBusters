package com.bugbusters.swe_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDTO {
    private int roomNumber;
    private int floor;
    private String type;
    private Boolean availability;  // Updated to Boolean
    private String description;
}
