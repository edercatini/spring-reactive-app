package com.ec.userservice.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "User not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}