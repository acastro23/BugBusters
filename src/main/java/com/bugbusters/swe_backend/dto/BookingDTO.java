package com.bugbusters.swe_backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDTO {

    private Long guestID;
    private GuestDTO myGuest;

    private Long roomID;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    private PaymentDTO myPayment;

    private String confirmationNumber;


}