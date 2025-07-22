package com.example.UserAuthenticationService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User extends BaseModel {
    private String name;
    private String emailId;
    private String password;
    private String phoneNumber;
    @ManyToMany
    private List<Role> rolesList = new ArrayList<>();
}
