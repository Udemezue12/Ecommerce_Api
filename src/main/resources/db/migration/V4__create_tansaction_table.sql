CREATE TABLE payment_transactions
(
    id                              BINARY(16)     NOT NULL,

    generated_reference             VARCHAR(255)   NOT NULL,

    payment_method                  VARCHAR(50)    NOT NULL,

    status                          VARCHAR(50)    NOT NULL,

    amount                          DECIMAL(38, 2) NOT NULL,

    order_processed                 BOOLEAN        NOT NULL DEFAULT FALSE,

    payment_provider_reference      VARCHAR(255)   NULL,

    payment_provider_transaction_id VARCHAR(255)   NULL,

    currency                        VARCHAR(50)    NULL,

    payment_channel                 VARCHAR(70)    NULL,

    user_id                         BINARY(16)     NULL,

    order_id                        BINARY(16)     NULL,

    created_at                      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,

    paid_at                         DATETIME       NULL,

    verified_at                     DATETIME       NULL,

    failed_at                       DATETIME       NULL,

    refunded_at                     DATETIME       NULL,

    CONSTRAINT pk_payment_transactions PRIMARY KEY (id),

    CONSTRAINT uq_transaction_payments_generated_reference
        UNIQUE (generated_reference),


    CONSTRAINT fk_payment_transactions_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE SET NULL,

    CONSTRAINT fk_payment_transactions_order
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
            ON DELETE SET NULL
);

CREATE INDEX idx_transaction_payments_status
    ON payment_transactions (status);

CREATE INDEX idx_transaction_payments_user
    ON payment_transactions (user_id);