package com.uchechukwu.store.repositories;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uchechukwu.store.entities.BlacklistedToken;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, UUID> {
    boolean existsByJti(String jti);

    void deleteByExpiresAtBefore(OffsetDateTime time);
     @Modifying
    @Query("DELETE FROM BlacklistedToken")
    void deleteAllTokens();
}
