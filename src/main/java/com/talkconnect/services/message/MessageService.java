package com.talkconnect.services.message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.talkconnect.models.entities.message.Message;
import com.talkconnect.models.entities.user.User;
import com.talkconnect.repositories.MessageRepository;
import com.talkconnect.repositories.UserRepository;
import com.talkconnect.services.message.requests.MessageRequest;

@Component
public class MessageService {
    @Autowired
    MessageRepository repository;

    @Autowired
    UserRepository userRepository;

    public Message insert(Long senderId, Long receiverId, MessageRequest request){
        User sender = userRepository.getReferenceById(senderId);
        User receiver = userRepository.getReferenceById(receiverId);
        var message = Message.builder()
        .createdAt(LocalDateTime.now())
        .deleted(false)
        .sender(sender)
        .receiver(receiver)
        .content(request.getContent())
        .isRead(false)
        .build();
        return repository.save(message);
    }

    public Message roomMessage(Long senderId, MessageRequest request){
        User sender = userRepository.getReferenceById(senderId);
        var message = Message.builder()
        .createdAt(LocalDateTime.now())
        .deleted(false)
        .sender(sender)
        .content(request.getContent())
        .isRead(false)
        .build();
    return message;
}

    public List<Message> getAllFriendsMessages(Long senderId, Long receiverId) {
        List<Message> list = repository.findMessagesBetweenTwoUsers(senderId, receiverId);
        // Incluindo o filtro para ambos os sentidos (sender -> receiver ou receiver -> sender)
        System.out.println("Lista completaaaaaaaaaaaaaaaaaaaa: "+list);
        return list;
    }

    public List<Message> getAllFriendsUnreadMessages(Long userId){
        User user = userRepository.getReferenceById(userId);
        return user.getReceivedMessages()
        .stream()
        .filter(message -> message.getIsRead() == false)
        .collect(Collectors.toList());
    }
   

   
}
