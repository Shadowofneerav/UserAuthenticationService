package com.example.UserAuthenticationService.controllers;

import com.example.UserAuthenticationService.dtos.SigninDto;
import com.example.UserAuthenticationService.dtos.SignupDto;
import com.example.UserAuthenticationService.dtos.UserDto;
import com.example.UserAuthenticationService.dtos.ValidateTokenDto;
import com.example.UserAuthenticationService.models.User;
import com.example.UserAuthenticationService.services.IAuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    public ResponseEntity<UserDto> signin(@RequestBody SigninDto signinDto)
    {
//        return from(authService.login(signinDto.getEmailId(),signinDto.getPassword()));
        Pair<User,String> pair = authService.login(signinDto.getEmailId(),signinDto.getPassword());
        User user = pair.a;
        String token = pair.b;
        UserDto userDto = from(user);
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("cookie-settings",token);
        return new ResponseEntity<>(userDto,multiValueMap, HttpStatus.OK);
    }
    @PostMapping("/validatetoken")
    public boolean validatetoken(@RequestBody ValidateTokenDto token)
    {
        return authService.validateToken(token.getToken(), token.getUserId());
    }
    private UserDto from(User user)
    {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmailId(user.getEmailId());
        return userDto;
    }
}
