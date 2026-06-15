ALTER TABLE deliveries
    DROP FOREIGN KEY fk_delivery_inventory_owner;
ALTER TABLE deliveries
    DROP COLUMN inventory_owner_id;

ALTER TABLE deliveries
    ADD COLUMN payment_transaction_id BINARY(16) NOT NULL AFTER driver_id,
    ADD INDEX idx_delivery_payment_transaction (payment_transaction_id);

ALTER TABLE deliveries
    ADD CONSTRAINT fk_delivery_payment_transaction
        FOREIGN KEY (payment_transaction_id) REFERENCES payment_transactions (id)
            ON DELETE RESTRICT;