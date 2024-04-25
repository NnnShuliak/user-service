package com.example.demouserservice.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;


public class AgeValidator implements ConstraintValidator<AgeConstraint, LocalDate> {
    private Integer minimumAge;

    @Override
    public void initialize(AgeConstraint constraintAnnotation) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
            minimumAge = Integer.valueOf(properties.getProperty("user.minAge"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        LocalDate today = LocalDate.now();
        LocalDate minimumDate = today.minusYears(minimumAge);
        return value.isBefore(minimumDate);
    }
}
