package com.talkconnect.controllers.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkconnect.services.authentication.AuthenticationService;
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

    // Optionally, you can also set the refresh token as a separate HttpOnly cookie if needed

    return ResponseEntity.ok(authenticationResponse);
}
}
