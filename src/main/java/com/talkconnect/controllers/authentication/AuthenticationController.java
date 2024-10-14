package com.talkconnect.controllers.authentication;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.talkconnect.services.authentication.AuthenticationService;
import com.talkconnect.services.authentication.requests.AuthenticationRequest;
import com.talkconnect.services.authentication.requests.RegisterRequest;
import com.talkconnect.services.authentication.responses.AuthenticationResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService service;

    @PostMapping("/register")
     public ResponseEntity<AuthenticationResponse> register(
    @RequestBody RegisterRequest request,
    HttpServletResponse httpResponse // Inject HttpServletResponse
) {
  
    AuthenticationResponse authenticationResponse = service.register(request);
    // Set the access token as an HttpOnly cookie in the response
    Cookie tokenCookie = new Cookie("token", authenticationResponse.getAccessToken());
    tokenCookie.setHttpOnly(true); // Set HttpOnly flag
    tokenCookie.setPath("/"); // Set cookie path as needed
    httpResponse.addCookie(tokenCookie);


    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(authenticationResponse.getId()).toUri();

    return ResponseEntity.created(uri).body(authenticationResponse);
}


 @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse httpResponse
    ) {
        AuthenticationResponse authenticationResponse = service.authenticate(request);

        Cookie tokenCookie = new Cookie("token", authenticationResponse.getAccessToken());
        tokenCookie.setHttpOnly(true); // Set HttpOnly flag
        tokenCookie.setPath("/"); // Set cookie path as needed
        httpResponse.addCookie(tokenCookie);


        return ResponseEntity.ok(authenticationResponse);
    }
}
