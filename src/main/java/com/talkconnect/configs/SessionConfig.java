package com.talkconnect.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class SessionConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setSameSite(null); // Configura SameSite como "None"
        serializer.setUseSecureCookie(false); // Garante que o cookie seja enviado apenas em conexões HTTPS seguras
        return serializer;
    }
}