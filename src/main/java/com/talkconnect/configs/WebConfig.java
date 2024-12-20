package com.talkconnect.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${domain.url}")
    private String domainUrl;

    @Value("${ip.url}")
    private String ipUrl;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000", "http://10.0.0.191:3000/app", domainUrl , "http://10.0.0.191", ipUrl) // Permitir requisições de qualquer origem
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Adicionando suporte para o método OPTIONS
            .allowedHeaders("*")
            .allowCredentials(true) // Permitir credenciais (cookies)
            .maxAge(3600); // Definindo o tempo de vida do preflight request para 1 hora
    }
}