package com.uchechukwu.store.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "spring.jwt")
@Data
public class JwtConfig {
    private String secret;
    private Long accessExpiration;
    private Long refreshExpiration;

    public SecretKey getSecretKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }
    
}
