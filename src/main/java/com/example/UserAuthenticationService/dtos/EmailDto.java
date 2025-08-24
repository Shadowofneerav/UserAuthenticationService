package com.example.UserAuthenticationService.dtos;

import lombok.Data;

@Data
public class EmailDto {
    private String email;
    private String subject;
    private String message;
}
