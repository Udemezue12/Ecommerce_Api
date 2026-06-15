package com.uchechukwu.store.core;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.uchechukwu.store.events.BlacklistedTokenEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisListener {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PREFIX = "blacklisted:";

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTokenBlacklisted(
            BlacklistedTokenEvent event) {
        try {
            long ttl = event.getExpiration().getTime()
                    - System.currentTimeMillis();

            if (ttl > 0) {

                redisTemplate.opsForValue().set(
                        PREFIX + event.getJti(),
                        true,
                        Duration.ofMillis(ttl));
            }

        } catch (Exception e) {
            log.error("Redis unavailable while blacklisting token: {}", e.getMessage());
        }

    }
}
