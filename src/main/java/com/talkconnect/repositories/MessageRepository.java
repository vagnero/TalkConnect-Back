package com.talkconnect.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.talkconnect.models.entities.message.Message;

public interface  MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE (m.sender.id = :userId1 AND m.receiver.id = :userId2) OR (m.sender.id = :userId2 AND m.receiver.id = :userId1)")
    List<Message> findMessagesBetweenTwoUsers(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

}
