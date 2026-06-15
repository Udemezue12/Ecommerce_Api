package com.uchechukwu.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.uchechukwu.store.enums.IdempotencyStatus;

@Entity
@Table(
    name = "idempotency_records",
    indexes = {
        @Index(
            name = "idx_request_hash",
            columnList = "request_hash"
        ),
        @Index(
            name = "idx_created_at",
            columnList = "created_at"
        )
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_idempotency_key",
            columnNames = "idempotency_key"
        )
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdempotencyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(
        name = "idempotency_key",
        columnDefinition = "BINARY(16)",
        nullable = false
    )
    private UUID idempotencyKey;

    @Column(name = "request_hash", nullable = false)
    private String requestHash;

    @Column(name = "http_method", nullable = false)
    private String httpMethod;

    @Column(name = "request_path", nullable = false)
    private String requestPath;

    @Column(name = "resource_id")
    private String resourceId;

    @Column(name = "resource_type")
    private String resourceType;

    @Column(name = "response_class")
    private String responseClass;

    @Column(name = "status_code")
    private Integer statusCode;

    @Lob
    @Column(name = "response_body", columnDefinition = "LONGTEXT")
    private String responseBody;

    @Lob
    @Column(name = "response_header", columnDefinition = "LONGTEXT")
    private String responseHeaders;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "exception_class")
    private String exceptionClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IdempotencyStatus status;

    @Column(name = "locked_at")
    private Instant lockedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}