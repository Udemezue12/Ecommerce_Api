ALTER TABLE addresses
    ADD COLUMN local_government VARCHAR(100) NULL,

    ADD COLUMN deleted          BOOLEAN      NOT NULL DEFAULT FALSE,
    ADD COLUMN deleted_at       DATETIME     NULL;

ALTER TABLE profiles
    DROP COLUMN local_government,
    DROP COLUMN deleted,
    DROP COLUMN deleted_at;
