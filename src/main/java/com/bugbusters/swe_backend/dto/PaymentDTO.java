package com.bugbusters.swe_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private String cardNumber;              //AC1118 -- changed card number and cvv to a string
    private String cvv;
    private String expDate;
    private String paymentDate;
    private String firstName;
    private String lastName;

    //AC1118 -- All changes by Alex start here
    private Long guestID;
}