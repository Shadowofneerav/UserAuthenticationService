package com.example.UserAuthenticationService.controllers;

import com.example.UserAuthenticationService.dtos.SigninDto;
import com.example.UserAuthenticationService.dtos.SignupDto;
import com.example.UserAuthenticationService.dtos.UserDto;
import com.example.UserAuthenticationService.dtos.ValidateTokenDto;
import com.example.UserAuthenticationService.models.User;
import com.example.UserAuthenticationService.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;
    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupDto sigupDto)
    {
        return from(authService.signup(sigupDto.getName(),sigupDto.getEmailId(),sigupDto.getPassword(),sigupDto.getPhoneNumber()));
    }
    @PostMapping("/signin")
    public UserDto signin(@RequestBody SigninDto signinDto)
    {
        return from(authService.login(signinDto.getEmailId(),signinDto.getPassword()));
    }
    @PostMapping("/validatetoken")
    public boolean validatetoken(@RequestBody ValidateTokenDto token)
    {
        return false;
    }
    private UserDto from(User user)
    {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmailId(user.getEmailId());
        return userDto;
    }
}
