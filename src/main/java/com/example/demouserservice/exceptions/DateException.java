package com.example.demouserservice.exceptions;

import java.time.LocalDate;

public class DateException extends RuntimeException{

    public DateException(LocalDate from, LocalDate to) {
        super(String.format("Date from:%s is greater than to:%s",from.toString(),to.toString()));
    }
}
