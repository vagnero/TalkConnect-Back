package com.talkconnect.controllers.message;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.talkconnect.models.entities.message.Message;
import com.talkconnect.services.authentication.AuthenticationService;
import com.talkconnect.services.message.MessageService;
import com.talkconnect.services.message.requests.MessageRequest;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "message")
public class MessageController {
    @Autowired
    MessageService service;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate; // Injetar o SimpMessagingTemplat
    @Autowired
    AuthenticationService authenticationService;

//     @PostMapping("/send/{receiverId}")
//     public ResponseEntity<Message> insertMessage(@PathVariable Long receiverId, @RequestBody MessageRequest request, HttpServletRequest httpRequest) {
//     System.out.println(request.getContent());
//     Long senderId = Long.valueOf(authenticationService.getUserIdFromToken(httpRequest).toString());
//     Message message = service.insert(senderId, receiverId, request);
//     URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(message.getId()).toUri();

//     return ResponseEntity.created(uri).body(message);
// }

@PostMapping("/send/{receiverId}")
public ResponseEntity<Message> insertMessage(@PathVariable Long receiverId, @RequestBody MessageRequest request, HttpServletRequest httpRequest) {
    Long senderId = Long.valueOf(authenticationService.getUserIdFromToken(httpRequest).toString());
    Message message = service.insert(senderId, receiverId, request);
    
    // Enviar a mensagem através do WebSocket
    messagingTemplate.convertAndSend("/topic/messages/" + receiverId, message);

    // Se quiser que o remetente também receba a mensagem, pode adicionar essa linha
   // messagingTemplate.convertAndSend("/topic/messages/" + senderId, message);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(message.getId()).toUri();
    return ResponseEntity.created(uri).body(message);
}

@GetMapping(value = "/get/{friendId}")
public ResponseEntity<List<Message>> findAllMessages(HttpServletRequest request, @PathVariable Long friendId) {
    Long senderId = Long.valueOf(authenticationService.getUserIdFromToken(request));
    List<Message> list = service.getAllFriendsMessages(senderId, friendId);
    return ResponseEntity.ok().body(list);
}

@GetMapping(value = "/get/unread")
public ResponseEntity<List<Message>> getAllFriendsUnreadMessages(HttpServletRequest request) {
    Long senderId = Long.valueOf(authenticationService.getUserIdFromToken(request));
    List<Message> list = service.getAllFriendsUnreadMessages(senderId);
    return ResponseEntity.ok().body(list);
}
    

}
