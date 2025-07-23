package com.example.UserAuthenticationService.repos;


import com.example.UserAuthenticationService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
//    public User save(User user);
    Optional<User> findByEmail(String email);
    User save(User user);
}
