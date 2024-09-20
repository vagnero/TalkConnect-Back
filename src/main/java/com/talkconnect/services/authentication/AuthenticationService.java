package com.talkconnect.services.authentication;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talkconnect.models.entities.user.User;
import com.talkconnect.repositories.UserRepository;
import com.talkconnect.services.authentication.requests.AuthenticationRequest;
import com.talkconnect.services.authentication.requests.RegisterRequest;
import com.talkconnect.services.authentication.responses.AuthenticationResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    //Método de cadastro
    public AuthenticationResponse register(RegisterRequest request){
        var user = User.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(request.getPassword())
        .role(request.getRole())
        .deleted(false) // Definindo como false
        .createdAt(LocalDateTime.now()) // Definindo a data atual
        .build(); // Não se esqueça de chamar o método build()
        
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(savedUser.getId(), user);
        var refreshToken = jwtService.generateRefreshToken(savedUser.getId(), user);
        
        return AuthenticationResponse.builder()
        .id(savedUser.getId())
        .role(savedUser.getRole())
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
    }

    //Método de login
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
        var jwtToken = jwtService.generateToken(user.getId(), user);
        var refreshToken = jwtService.generateRefreshToken(user.getId(), user);
        return AuthenticationResponse.builder()
        .id(user.getId())
        .role(user.getRole())
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();

}

public void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response
) throws IOException, StreamWriteException, DatabindException {
    Cookie[] cookies = request.getCookies();
    String refreshToken = null;
    String userEmail = null;

    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) { // Nome do cookie
                refreshToken = cookie.getValue();
                break; // Saia do loop se o cookie for encontrado
            }
        }
    }

    if (refreshToken != null) {
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user.getId(), user);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}




public Integer getUserIdFromCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) { // Verifique o nome do cookie
                return Integer.valueOf(cookie.getValue()); // Converta o valor do cookie para Integer
            }
        }
    }
    return 0; // Retorna 0 se o cookie não for encontrado
}

}
