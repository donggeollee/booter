package com.boot.meal.config.security;

import com.boot.meal.security.provider.JwtAuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.InvalidKeyException;

@Configuration
public class JwtConfig {

    @Value("${jwt.base64-secret}")
    private String base64Secret;

    @Bean
    public JwtAuthTokenProvider jwtProvider() {
        try {
            return new JwtAuthTokenProvider(base64Secret);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
}