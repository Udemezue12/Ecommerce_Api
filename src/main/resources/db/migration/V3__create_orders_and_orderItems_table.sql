CREATE TABLE IF NOT EXISTS orders
(
    id          BINARY(16)     NOT NULL
                                        DEFAULT (UUID_TO_BIN(UUID())),
    customer_id BINARY(16)     NOT NULL,
    status      VARCHAR(30)    NOT NULL,
    created_at  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10, 2) NOT NULL,
    updated_at  DATETIME                DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_orders PRIMARY KEY (id),

    CONSTRAINT fk_users_orders
        FOREIGN KEY (customer_id)
            REFERENCES users (id)
            ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS order_items
(
    id          BINARY(16)     NOT NULL
                                        DEFAULT (UUID_TO_BIN(UUID())),

    order_id    BINARY(16)     NOT NULL,
    product_id  BINARY(16)     NOT NULL,

    quantity    INT            NOT NULL DEFAULT 1,
    unit_price  DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,

    CONSTRAINT pk_order_items PRIMARY KEY (id),

    CONSTRAINT uq_order_product
        UNIQUE (order_id, product_id),

    CONSTRAINT chk_quantity
        CHECK (quantity > 0),

    CONSTRAINT chk_unit_price
        CHECK (unit_price >= 0),

    CONSTRAINT chk_total_price
        CHECK (total_price >= 0),

    CONSTRAINT fk_orders_order_items
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_products_order_items
        FOREIGN KEY (product_id)
            REFERENCES products (id)
            ON DELETE RESTRICT
);
CREATE INDEX idx_orders_customer_id
    ON orders (customer_id);

CREATE INDEX idx_order_items_order_id
    ON order_items (order_id);

CREATE INDEX idx_order_items_product_id
    ON order_items (product_id);