package com.example.demouserservice.exceptions;

public class NoSuchUserException extends RuntimeException{
    public NoSuchUserException(Long userId) {
        super("User with id:"+userId+" not found");
    }
}
