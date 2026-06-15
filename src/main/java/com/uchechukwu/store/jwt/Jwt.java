package com.uchechukwu.store.jwt;

import com.uchechukwu.store.enums.JwtType;
import com.uchechukwu.store.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
public class Jwt {
    private final Claims claims;
    private final SecretKey secretKey;

    public boolean isExpired() {
        return claims.getExpiration()
                .before(new Date());

    }

    public UUID getUserId() {
        return UUID.fromString(claims.getSubject());
    }

    public String getJti() {
        return claims.getId();
    }

    public JwtType getType() {
        return JwtType.valueOf(
                claims.get("type", String.class));
    }

    public Date getExpiration() {
        return claims.getExpiration();
    }

    public UserRole getUserRole() {
        return UserRole.valueOf(claims.get("role", String.class));


    }

    public String toString() {
        return Jwts.builder().claims(claims).signWith(secretKey).compact();

    }
}
