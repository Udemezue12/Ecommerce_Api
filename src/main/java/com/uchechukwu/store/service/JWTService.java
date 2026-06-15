
package com.uchechukwu.store.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.uchechukwu.store.config.JwtConfig;
import com.uchechukwu.store.entities.User;
import com.uchechukwu.store.jwt.Jwt;
import com.uchechukwu.store.enums.JwtType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final JwtConfig jwtConfig;

    public Jwt generateAccessToken(User user) {

        return generateJwtToken(user, jwtConfig.getAccessExpiration() * 1000, JwtType.ACCESS.name());
    }

    public Jwt generateRefreshToken(User user) {

        return generateJwtToken(user, jwtConfig.getRefreshExpiration() * 1000, JwtType.REFRESH.name());
    }

    public Jwt parseToken(String token){
        try{
            var claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (JwtException e) {
            return null;
        }
    }

    private Jwt generateJwtToken(User user, Long expiration, String type) {
        String jti = UUID.randomUUID().toString();
        var claims = Jwts.claims()
                .id(jti)
                .subject(user.getId().toString())
                .add("name", user.getName())
                .add("email", user.getEmail())
                .add("role", user.getRole().name())
                .add("type", type)
                 
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expiration))
                .build();
        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}