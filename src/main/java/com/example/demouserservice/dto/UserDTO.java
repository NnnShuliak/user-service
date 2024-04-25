package com.example.demouserservice.dto;

import com.example.demouserservice.validation.AgeConstraint;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
public class UserDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Past
    @AgeConstraint
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @NotBlank
    private String address;

    @Size(min = 5, max = 15)
    private String phoneNumber;
}
