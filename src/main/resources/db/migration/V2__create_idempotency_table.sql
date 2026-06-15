CREATE TABLE idempotency_records
(
    id              BINARY(16)   NOT NULL,
    idempotency_key BINARY(16)   NOT NULL,
    request_hash    VARCHAR(255) NOT NULL,
    http_method     VARCHAR(50)  NOT NULL,
    request_path    VARCHAR(255) NOT NULL,
    resource_id     VARCHAR(255),
    resource_type   VARCHAR(255),
    response_class  VARCHAR(255),
    status_code     INT,
    response_body   LONGTEXT,
    response_header LONGTEXT,
    content_type    VARCHAR(255),
    error_message   VARCHAR(255),
    exception_class VARCHAR(255),
    status          VARCHAR(50)  NOT NULL,
    locked_at       TIMESTAMP    NULL,
    completed_at    TIMESTAMP    NULL,
    created_at      TIMESTAMP    NOT NULL,

    -- Primary Key
    PRIMARY KEY (id),

    -- Unique constraint
    CONSTRAINT uk_idempotency_key UNIQUE (idempotency_key),

    -- Indexes
    INDEX idx_request_hash (request_hash),
    INDEX idx_created_at (created_at),
    INDEX idx_method_path (http_method, request_path)
);

