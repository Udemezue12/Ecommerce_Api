ALTER TABLE deliveries
    ADD COLUMN picked_up_at DATETIME NULL,
    ADD COLUMN cancelled_at DATETIME NULL,
    ADD COLUMN failed_at    DATETIME NULL;

