package com.uchechukwu.store.verification;


import com.uchechukwu.store.configProperties.AuthProperties;
import com.uchechukwu.store.core.GetSecretKey;
import com.uchechukwu.store.fintech.FintechConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserVerification {

    private final AuthProperties properties;

    private final StringRedisTemplate redisTemplate;

    private final JobScheduler jobScheduler;

    private final Random random = new Random();

    public String generateVerifyToken(String email) {

        var key = GetSecretKey.getKeys(properties.getVerifyEmailSecretKey());

        return getJwtsBuilder(email, key, "verify_email");
    }

    public String generateResetToken(String email) {
        var key = GetSecretKey.getKeys(properties.getResetSecretKey());
        return getJwtsBuilder(email, key, "reset_email");
    }

    public String generateOtp(String email) {
        var otp = String.valueOf(100000 + random.nextInt(900000));
        redisTemplate.opsForValue().set(
                "otp:" + email,
                otp,
                FintechConstant.getMinuteTimeout(5)
        );

        return otp;
    }

    public String verifyOtp(String otp) {

        var keys =
                redisTemplate.keys("otp:*");

        if (keys.isEmpty()) {
            return null;
        }

        for (String key : keys) {

            var storedOtp =
                    redisTemplate.opsForValue().get(key);

            if (otp.equals(storedOtp)) {

                redisTemplate.delete(key);

                return key.replace("otp:", "");
            }
        }

        return null;
    }

    public String verifyResetToken(String token) {

        try {
            var key = GetSecretKey.getKeys(properties.getResetSecretKey());

            var claims = getJwtsParser(token, key);
            return claims.getSubject();

        } catch (JwtException ex) {
            return null;
        }
    }

    public String verifyVerifyToken(String token) {

        try {
            var key = GetSecretKey.getKeys(properties.getVerifyEmailSecretKey());

            var claims = getJwtsParser(token, key);
            return claims.getSubject();

        } catch (JwtException ex) {
            return null;
        }
    }


    private static Claims getJwtsParser(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private static String getJwtsBuilder(String email, SecretKey key, String getType) {
        return Jwts.builder()
                .subject(email)
                .claim("type", getType)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 3600_000
                        )
                )
                .signWith(key)
                .compact();
    }


}
