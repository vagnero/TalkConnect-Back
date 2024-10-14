package com.talkconnect.controllers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkconnect.models.entities.message.Message;
import com.talkconnect.models.entities.user.User;
import com.talkconnect.services.authentication.AuthenticationService;
import com.talkconnect.services.user.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    UserService service;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping
        public ResponseEntity<List<User>> findAll(){
            List<User> list = service.findAll();
                return ResponseEntity.ok().body(list);
        
    }



    @GetMapping(value = "/myfriends")
    public ResponseEntity<List<User>> findAllFriends(HttpServletRequest request){
        Long id = Long.valueOf(authenticationService.getUserIdFromToken(request).toString());
            return ResponseEntity.ok().body(service.findAllFriends(id));
    
}
    @GetMapping(value = "/getid")
    public ResponseEntity<Long> getUserId(HttpServletRequest request){
        Long id = Long.valueOf(authenticationService.getUserIdFromToken(request).toString());
        return ResponseEntity.ok().body(id);

    }

@GetMapping(value = "/friendsrequests")
public ResponseEntity<List<User>> findFriendsRequests(HttpServletRequest request){
    Long id = Long.valueOf(authenticationService.getUserIdFromToken(request).toString());
        //System.out.println(  user.getFriends().toString());
        return ResponseEntity.ok().body(service.findReceivedPendingFriends(id));

}

@GetMapping(value = "/messages/{friendId}")
public ResponseEntity<List<Message>> findAllMessages(HttpServletRequest request, @PathVariable Long friendId){
    Long id = Long.valueOf(authenticationService.getUserIdFromToken(request).toString());
    List<Message> list = service.allUserMessages(id, friendId);
    return ResponseEntity.ok().body(list);

}

@GetMapping(value = "allusers")
public ResponseEntity<List<User>> getAllUsersLessYou(HttpServletRequest request){
    Long id = Long.valueOf(authenticationService.getUserIdFromToken(request).toString());
    List<User> list = service.getAllUserLessYou(id);
        return ResponseEntity.ok().body(list);

}


}
