package com.bugbusters.swe_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private Long cardNumber;
    private int cvv;
    private String expDate;
    private String paymentDate;
    private String firstName;
    private String lastName;
}