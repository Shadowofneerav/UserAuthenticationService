package com.example.UserAuthenticationService.dtos;

import lombok.Data;

@Data
public class SignupDto {
    private String name;
    private String emailId;
    private String password;
    private String phoneNumber;
}
