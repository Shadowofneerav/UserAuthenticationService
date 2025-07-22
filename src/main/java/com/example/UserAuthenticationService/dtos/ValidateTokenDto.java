package com.example.UserAuthenticationService.dtos;

import lombok.Data;

@Data
public class ValidateTokenDto {
    private String token;
    private String userId;
}
