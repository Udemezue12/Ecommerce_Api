ALTER TABLE addresses
    ADD COLUMN default_user_id BINARY(16)
        GENERATED ALWAYS AS (
            IF(is_default = TRUE, user_id, NULL)
            ) STORED,
    ADD UNIQUE INDEX uk_one_default_per_user (default_user_id);