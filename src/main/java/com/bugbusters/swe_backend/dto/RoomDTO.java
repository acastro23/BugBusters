package com.bugbusters.swe_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RoomDTO {

    @Positive(message = "The room number should be a positive value")
    private int roomNumber;

    @Positive(message = "Floor number should be a positive integer.")
    private int floor;

    @NotBlank(message = "Room type selection is required!")
    private String type;
    private Boolean availability;

    @Size(max = 500, message = "Room description should be less than or equal to 500 characters.")
    private String description;

    @Positive(message = "Prices can't be negative")
    private BigDecimal price;
}
