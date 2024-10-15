package com.talkconnect.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000", "http://10.0.0.182:3000", "https://10.0.0.190" , "http://10.0.0.190", "https://talkinvagner.shop") // Permitir requisições de qualquer origem
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Adicionando suporte para o método OPTIONS
            .allowedHeaders("*")
            .allowCredentials(true) // Permitir credenciais (cookies)
            .maxAge(3600); // Definindo o tempo de vida do preflight request para 1 hora
    }
}