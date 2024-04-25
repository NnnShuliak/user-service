package com.example.demouserservice.controller;

import com.example.demouserservice.exceptions.DateException;
import com.example.demouserservice.exceptions.NoSuchUserException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<?> handleValidationFailing(ConstraintViolationException ex) {
        return createExceptionResponse(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoSuchUserException.class})
    protected ResponseEntity<?> handleUserNotFound(NoSuchUserException ex) {
        return createExceptionResponse(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DateException.class})
    protected ResponseEntity<?> handleDateException(DateException ex) {
        return createExceptionResponse(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> createExceptionResponse(String message,HttpStatus status){
        return new ResponseEntity<>(Map.of("error",message),status);
    }
}