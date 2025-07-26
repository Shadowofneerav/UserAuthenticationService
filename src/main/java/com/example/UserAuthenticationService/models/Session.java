package com.example.UserAuthenticationService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Session extends BaseModel{
    @ManyToOne
    private User user;
    private String token;
    private SessionState sessionState;
}
