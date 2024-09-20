package com.talkconnect.controllers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkconnect.models.entities.user.User;
import com.talkconnect.services.user.UserService;

@RestController
@RequestMapping(value = "user/")
public class UserController {
    @Autowired
    UserService service;


    @GetMapping
        public ResponseEntity<List<User>> findAll(){
            List<User> list = service.findAll();
                return ResponseEntity.ok().body(list);
        
    }
}
