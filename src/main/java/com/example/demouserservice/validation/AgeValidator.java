package com.example.demouserservice.validation;


import com.example.demouserservice.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

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
