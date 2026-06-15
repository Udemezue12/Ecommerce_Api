ALTER TABLE profiles
    DROP FOREIGN KEY fk_profiles_user;


ALTER TABLE profiles
    ADD COLUMN user_id BINARY(16) NOT NULL,
    ADD CONSTRAINT uk_profiles_user_id UNIQUE (user_id); -- Enforces the One-to-One invariant


ALTER TABLE profiles
    ADD CONSTRAINT fk_profiles_user_new
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON DELETE CASCADE;