package com.talkconnect.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talkconnect.models.entities.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByEmail(String email);

    
}
