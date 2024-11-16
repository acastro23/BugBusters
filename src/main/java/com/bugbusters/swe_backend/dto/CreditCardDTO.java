package com.bugbusters.swe_backend.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardDTO {
    private Long cardNumber;
    private int cvv;
    private String firstName;
    private String lastName;
    private int expDate;
}
