package com.talkconnect.controllers.friendship;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.talkconnect.models.entities.friendship.FriendShip;
import com.talkconnect.services.authentication.AuthenticationService;
import com.talkconnect.services.friendship.FriendShipService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "friendship")
public class FriendShipController {
    @Autowired
    FriendShipService service;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate; // Injetar o SimpMessagingTemplat


    @PostMapping(value = "/create/{friendId}")
    public ResponseEntity<FriendShip> insert(HttpServletRequest request, @PathVariable Long friendId){
        Long id = Long.valueOf(authenticationService.getUserIdFromToken(request).toString());
        FriendShip entity = service.insert(id, friendId);

        messagingTemplate.convertAndSend("/topic/friends/" + friendId, entity);

        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(entity);
    }

    @PatchMapping(value = "/accept/{friendId}")
    public ResponseEntity<FriendShip> accept(HttpServletRequest request, @PathVariable Long friendId){
        Long id = Long.valueOf(authenticationService.getUserIdFromToken(request).toString());
        FriendShip entity = service.acceptFriendship(id, friendId);

        return ResponseEntity.ok().body(entity);
    }

    @PatchMapping(value = "/reject/{friendId}")
    public ResponseEntity<FriendShip> reject(HttpServletRequest request, @PathVariable Long friendId){
        Long id = Long.valueOf(authenticationService.getUserIdFromToken(request).toString());
        FriendShip entity = service.rejectFriendship(id, friendId);

        return ResponseEntity.ok().body(entity);
    }
 
}
