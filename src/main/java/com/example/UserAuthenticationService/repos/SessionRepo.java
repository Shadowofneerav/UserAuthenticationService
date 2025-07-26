package com.example.UserAuthenticationService.repos;

import com.example.UserAuthenticationService.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepo extends JpaRepository<Session,Long> {
    Session save(Session session);


    Optional<Session> findByTokenAndUser_id(String token,Long userId);
}
