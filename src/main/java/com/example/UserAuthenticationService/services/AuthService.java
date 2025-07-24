package com.example.UserAuthenticationService.services;

import com.example.UserAuthenticationService.exceptions.InvalidCredentialsException;
import com.example.UserAuthenticationService.exceptions.UserAccountAlreadyPresentException;
import com.example.UserAuthenticationService.exceptions.UserAccountNotCreatedException;
import com.example.UserAuthenticationService.models.User;
import com.example.UserAuthenticationService.repos.UserRepo;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class AuthService implements IAuthService{
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User signup(String name, String email, String password, String phoneNumber) {
        Optional<User> user = userRepo.findByEmailId(email);
        if(user.isPresent())
        {
            throw new UserAccountAlreadyPresentException("User Account has already been present");
        }
        User saveduser = new User();
        saveduser.setName(name);
        saveduser.setEmailId(email);
        saveduser.setPassword(bCryptPasswordEncoder.encode(password));
        saveduser.setPhoneNumber(phoneNumber);
        saveduser = userRepo.save(saveduser);
        return saveduser;
    }

    @Override
    public User login(String email, String password) {
        Optional<User> user = userRepo.findByEmailId(email);
        if(user.isEmpty())
        {
            throw new UserAccountNotCreatedException("User Account has not been created");
        }
        User getuser = user.get();
        if(!bCryptPasswordEncoder.matches(password,getuser.getPassword()))
        {
            throw new InvalidCredentialsException("Invalid Credential are provided by User");
        }
        return getuser;
    }

    @Override
    public Boolean validateToken(String token, Long userId) {
        return null;
    }
}
