package com.bugbusters.swe_backend.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// transfer guest data around between front and back end.

@Getter
@Setter
public class GuestDTO {

    @NotBlank(message = "Please enter a first name")
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String fname;

    @NotBlank(message = "Please enter a last name")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String lname;

    @NotBlank(message = "Please enter an email address")
    @Email(message = "Please provide a valid email address")
    private String email;

    @Size(max = 15, message = "Phone number must be formatted like (###)-###-####")
    private String phoneNumber;
}
