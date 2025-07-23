package com.example.UserAuthenticationService.exceptions;

public class UserAccountNotCreatedException extends RuntimeException {
    public UserAccountNotCreatedException(String message) {
        super(message);
    }
}
