package com.example.UserAuthenticationService.services;

import com.example.UserAuthenticationService.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class AuthService implements IAuthService{
    @Override
    public User signup(String name, String email, String password, String phoneNumber) {
        return null;
    }

    @Override
    public User login(String email, String password) {
        return null;
    }

    @Override
    public Boolean validateToken(String token, Long userId) {
        return null;
    }
}
