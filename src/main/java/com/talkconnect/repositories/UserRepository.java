package com.talkconnect.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.talkconnect.models.entities.friendship.FriendShip;
import com.talkconnect.models.entities.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByUsername(String username);
        @Query("SELECT f FROM FriendShip f WHERE f.user = :user")
    Optional<List<FriendShip>> findAllFriends(User user);

    
}
