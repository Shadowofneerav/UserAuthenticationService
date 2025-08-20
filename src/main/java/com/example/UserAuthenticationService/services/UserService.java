package com.example.UserAuthenticationService.services;

import com.example.UserAuthenticationService.models.User;
import com.example.UserAuthenticationService.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    public User getUserbyId(Long id)
    {
        return userRepo.findById(id).get();
    }
}
