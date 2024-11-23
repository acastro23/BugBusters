package com.bugbusters.swe_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmationDTO {
    private int confirmationID;
    private int bookingID;
    private String confString;
}
