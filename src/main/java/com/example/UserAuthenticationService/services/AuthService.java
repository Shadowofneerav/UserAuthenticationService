package com.example.UserAuthenticationService.services;

import com.example.UserAuthenticationService.models.Session;
import com.example.UserAuthenticationService.models.SessionState;
import com.example.UserAuthenticationService.repos.SessionRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import org.antlr.v4.runtime.misc.Pair;
import com.example.UserAuthenticationService.exceptions.InvalidCredentialsException;
import com.example.UserAuthenticationService.exceptions.UserAccountAlreadyPresentException;
import com.example.UserAuthenticationService.exceptions.UserAccountNotCreatedException;
import com.example.UserAuthenticationService.models.User;
import com.example.UserAuthenticationService.repos.UserRepo;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Primary
public class AuthService implements IAuthService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SessionRepo sessionRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecretKey secretKey;
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
    public Pair<User,String> login(String email, String password) {
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
        //Creating token and saving in Database
        //Generate JWT Token

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId",getuser.getId());
        claims.put("iss","scaler");
        Long nowInMillis = System.currentTimeMillis();
        claims.put("gen",nowInMillis);
        claims.put("exp",nowInMillis+100000);
        claims.put("scope",getuser.getRolesList());

        String token = Jwts.builder().claims(claims).signWith(secretKey).compact();
        Session session = new Session();
        session.setUser(getuser);
        session.setSessionState(SessionState.ACTIVE);
        session.setToken(token);

        sessionRepo.save(session);
        return new Pair<User,String>(getuser,token);
    }

    @Override
    public Boolean validateToken(String token, Long userId) {
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Optional<Session> session = sessionRepo.findByTokenAndUser_id(token, userId);
        if(session.isEmpty())
        {
            return false;
        }
        Session sessionuser = session.get();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Long exp = (long)claims.get("exp");
        Long currentTime = System.currentTimeMillis();
        if(currentTime>exp)
        {
            sessionuser.setSessionState(SessionState.INACTIVE);
            sessionRepo.save(sessionuser);
            return false;
        }
        return true;
    }
}
