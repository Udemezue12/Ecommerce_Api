package com.uchechukwu.store.core;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class GetSecretKey {
    public static SecretKey getKeys(String secretKey) {
        return Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }
}
