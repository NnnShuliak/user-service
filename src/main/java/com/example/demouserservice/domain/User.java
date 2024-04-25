package com.example.demouserservice.domain;


import com.example.demouserservice.validation.AgeConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "user_seq",sequenceName = "user_id_seq",allocationSize = 1)
    @GeneratedValue(generator = "user_seq")
    private Long id;
    @NotBlank
    @Email
    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @NotBlank
    @Column(name = "firstName",nullable = false)
    private String firstName;
    @NotBlank
    @Column(name = "lastName",nullable = false)
    private String lastName;

    @NotNull
    @Past
    @AgeConstraint
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @NotBlank
    @Column(name = "address")
    private String address;
    @Column(name = "phoneNumber",unique = true)
    @Size(min = 5,max = 15)
    private String phoneNumber;



}
