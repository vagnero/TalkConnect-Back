package com.talkconnect.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.talkconnect.models.entities.friendship.FriendShip;
import com.talkconnect.models.entities.user.User;

public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {
    List<FriendShip> findByUser(User user);

    List<FriendShip> findByFriend(User friend);
    FriendShip findByUserAndFriend(User user, User friend);
}
