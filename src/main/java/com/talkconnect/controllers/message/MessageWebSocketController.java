package com.talkconnect.controllers.message;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.talkconnect.models.entities.message.Message;

@Controller
public class MessageWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send") // Mapeia as mensagens enviadas para este endpoint
    public void sendMessage(Message message) {
        // Envia a mensagem para o tópico apropriado
        messagingTemplate.convertAndSend("/topic/messages/" + message.getReceiver().getId(), message);
    }
}