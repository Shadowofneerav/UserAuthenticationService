package com.example.UserAuthenticationService.repos;


import com.example.UserAuthenticationService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
//    public User save(User user);
}
