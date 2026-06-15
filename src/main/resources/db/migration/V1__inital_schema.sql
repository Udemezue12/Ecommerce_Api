CREATE TABLE users
(
    id        BINARY(16)   NOT NULL
                                    DEFAULT (UUID_TO_BIN(UUID())),
    name      VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,

    is_active BOOLEAN      NOT NULL DEFAULT TRUE,

    role      VARCHAR(50)  NOT NULL DEFAULT 'USER',

    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id   BINARY(16)   NOT NULL
        DEFAULT (UUID_TO_BIN(UUID())),
    name VARCHAR(255) NOT NULL,

    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE products
(
    id             BINARY(16)     NOT NULL
                                           DEFAULT (UUID_TO_BIN(UUID())),
    name           VARCHAR(255)   NOT NULL,
    price          DECIMAL(10, 2) NOT NULL,
    description    LONGTEXT       NOT NULL,
    stock_quantity INT            NOT NULL DEFAULT 1,
    category_id    BINARY(16)     NULL,

    CONSTRAINT pk_products PRIMARY KEY (id),

    CONSTRAINT fk_products_category
        FOREIGN KEY (category_id)
            REFERENCES categories (id)
            ON DELETE NO ACTION
);

CREATE INDEX idx_products_category
    ON products (category_id);

CREATE TABLE profiles
(
    id             BINARY(16)  NOT NULL
                                DEFAULT (UUID_TO_BIN(UUID())),

    bio            LONGTEXT    NULL,
    phone_number   VARCHAR(15) NULL,
    date_of_birth  DATE        NULL,

    loyalty_points INT UNSIGNED DEFAULT 0 NULL,

    CONSTRAINT pk_profiles PRIMARY KEY (id),

    CONSTRAINT fk_profiles_user
        FOREIGN KEY (id)
            REFERENCES users (id)
            ON DELETE NO ACTION
);

CREATE TABLE addresses
(
    id      BINARY(16)   NOT NULL
        DEFAULT (UUID_TO_BIN(UUID())),

    street  VARCHAR(255) NOT NULL,
    city    VARCHAR(255) NOT NULL,
    state   VARCHAR(255) NOT NULL,
    zip     VARCHAR(255) NOT NULL,

    user_id BINARY(16)   NOT NULL,

    CONSTRAINT pk_addresses PRIMARY KEY (id),

    CONSTRAINT fk_addresses_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE NO ACTION
);

CREATE INDEX idx_addresses_user
    ON addresses (user_id);

CREATE TABLE wishlist
(
    product_id BINARY(16) NOT NULL
        DEFAULT (UUID_TO_BIN(UUID())),
    user_id    BINARY(16) NOT NULL
        DEFAULT (UUID_TO_BIN(UUID())),

    CONSTRAINT pk_wishlist
        PRIMARY KEY (product_id, user_id),

    CONSTRAINT fk_wishlist_product
        FOREIGN KEY (product_id)
            REFERENCES products (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_wishlist_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE NO ACTION
);

CREATE INDEX idx_wishlist_user
    ON wishlist (user_id);

CREATE TABLE carts
(
    id           BINARY(16) NOT NULL
        DEFAULT (UUID_TO_BIN(UUID())),

    date_created TIMESTAMP  NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    user_id      BINARY(16) NULL,
    date_updated TIMESTAMP  NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_carts PRIMARY KEY (id),
    CONSTRAINT fk_users_carts
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE NO ACTION
);

CREATE TABLE cart_items
(
    id         BINARY(16) NOT NULL
                                   DEFAULT (UUID_TO_BIN(UUID())),

    product_id BINARY(16) NOT NULL,

    quantity   INT        NOT NULL DEFAULT 1,

    cart_id    BINARY(16) NOT NULL,

    CONSTRAINT pk_cart_items PRIMARY KEY (id),

    CONSTRAINT fk_cart_items_product
        FOREIGN KEY (product_id)
            REFERENCES products (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_cart_items_cart
        FOREIGN KEY (cart_id)
            REFERENCES carts (id)
            ON DELETE CASCADE
);

CREATE TABLE blacklisted_tokens
(
    id         BINARY(16)   NOT NULL
        DEFAULT (UUID_TO_BIN(UUID())),

    jti        VARCHAR(255) NOT NULL,

    token_type VARCHAR(50)  NOT NULL,

    expires_at DATETIME(6)  NOT NULL,

    revoked_at DATETIME(6)  NOT NULL,

    CONSTRAINT pk_blacklisted_tokens PRIMARY KEY (id),

    CONSTRAINT uk_blacklisted_jti UNIQUE (jti)
);

CREATE INDEX idx_blacklisted_jti
    ON blacklisted_tokens (jti);
CREATE TABLE inventories
(
    id                 BINARY(16) NOT NULL,

    product_id         BINARY(16) NOT NULL,
    user_id            BINARY(16) NOT NULL,

    initial_quantity   INT        NOT NULL DEFAULT 0,
    available_quantity INT        NOT NULL DEFAULT 0,

    reserved_quantity  INT        NOT NULL DEFAULT 0,
    sold_quantity      INT        NOT NULL DEFAULT 0,


    created_at         TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP           DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_inventories PRIMARY KEY (id),
    CONSTRAINT fk_inventory_product
        FOREIGN KEY (product_id)
            REFERENCES products (id),

    CONSTRAINT fk_inventory_user
        FOREIGN KEY (user_id)
            REFERENCES users (id),

    UNIQUE KEY uk_product_owner (product_id, user_id)
);
CREATE TABLE inventory_transactions
(
    id               BINARY(16)   NOT NULL,

    inventory_id     BINARY(16)   NOT NULL,

    transaction_type ENUM (
        'STOCK_IN',
        'STOCK_OUT',
        'RESERVED',
        'RELEASED',
        'RETURNED'
        )                         NOT NULL,

    quantity         INT          NOT NULL,

    reference_id     VARCHAR(100) NOT NULL,

    notes            VARCHAR(500) NOT NULL,

    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_inventory_transactions PRIMARY KEY (id),
    CONSTRAINT fk_inventory_transaction_inventory
        FOREIGN KEY (inventory_id)
            REFERENCES inventories (id)
);
CREATE TABLE inventory_reservations
(
    id           BINARY(16) NOT NULL,

    inventory_id BINARY(16) NOT NULL,
    cart_item_id BINARY(16) NOT NULL,

    quantity     INT        NOT NULL,

    expires_at   TIMESTAMP  NOT NULL,

    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_inventory_reservations PRIMARY KEY (id),
    CONSTRAINT fk_reservation_inventory
        FOREIGN KEY (inventory_id)
            REFERENCES inventories (id),

    CONSTRAINT fk_reservation_cart_item
        FOREIGN KEY (cart_item_id)
            REFERENCES cart_items (id)
);