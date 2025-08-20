package com.example.UserAuthenticationService.controllers;

import com.example.UserAuthenticationService.dtos.UserDto;
import com.example.UserAuthenticationService.models.User;
import com.example.UserAuthenticationService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        User user = userService.getUserbyId(id);
        if(user==null)
        {
            return null;
        }
        return from(user);
    }
    private UserDto from(User user)
    {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmailId(user.getEmailId());
        return userDto;
    }
}
