package com.uchechukwu.store.entities;


import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.uchechukwu.store.enums.JwtType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "blacklisted_tokens",
        indexes = {
                @Index(name = "idx_blacklisted_jti", columnList = "jti")
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name="id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String jti;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JwtType tokenType;

    @Column(nullable = false)
    private OffsetDateTime expiresAt;

    @Column(nullable = false)
    private OffsetDateTime revokedAt;

    @PrePersist
    public void prePersist() {
        revokedAt = OffsetDateTime.now();
    }
}