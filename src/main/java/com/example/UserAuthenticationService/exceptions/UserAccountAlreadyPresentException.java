package com.example.UserAuthenticationService.exceptions;

public class UserAccountAlreadyPresentException extends RuntimeException {
    public UserAccountAlreadyPresentException(String message) {
        super(message);
    }
}
