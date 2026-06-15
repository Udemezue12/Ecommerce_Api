package com.uchechukwu.store.service;

import com.uchechukwu.store.entities.BlacklistedToken;
import com.uchechukwu.store.events.BlacklistedTokenEvent;
import com.uchechukwu.store.jwt.Jwt;
import com.uchechukwu.store.repositories.BlacklistedTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlacklistedTokenService {
    private final BlacklistedTokenRepository repository;
    private final ApplicationEventPublisher publisher;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String PREFIX = "blacklisted:";

    public boolean isBlacklisted(String jti) {


        try {

            var exists =
                    redisTemplate.hasKey(PREFIX + jti);

            if (exists) {
                return true;
            }

        } catch (Exception e) {


            log.error(
                    "Redis unavailable during blacklist check: {}",
                    e.getMessage());
        }


        var dbExists =
                repository.existsByJti(jti);


        if (dbExists) {

            try {

                redisTemplate.opsForValue().set(
                        PREFIX + jti,
                        true,
                        Duration.ofDays(7));

            } catch (Exception e) {

                log.error(
                        "Redis unavailable during cache rehydration: {}",
                        e.getMessage());
            }
        }

        return dbExists;
    }

    @Transactional
    public void blacklist(Jwt jwt) {

        if (jwt == null) {
            return;
        }
        var jti = jwt.getJti();
        var convertedTime = convert(jwt.getExpiration());

        if (repository.existsByJti(jti)) {
            return;
        }

        repository.save(
                BlacklistedToken.builder()
                        .jti(jti)
                        .tokenType(jwt.getType())
                        .expiresAt(convertedTime)
                        .build());
        publisher.publishEvent(
                new BlacklistedTokenEvent(
                        jti,
                        jwt.getExpiration()));
    }

    @Transactional
    public void cleanupExpiredTokens() {

        // repository.deleteByExpiresAtBefore(
        // OffsetDateTime.now());
        repository.deleteAllTokens();

        System.out.println(
                "Expired revoked tokens cleaned");
    }

    private OffsetDateTime convert(Date date) {
        return date.toInstant()
                .atOffset(ZoneOffset.UTC);
    }

}
