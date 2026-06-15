
CREATE TABLE deliveries (
    id BINARY(16) PRIMARY KEY,

    order_id BINARY(16) NOT NULL,
    customer_id BINARY(16) NOT NULL,

   
    driver_id BINARY(16) NULL,

    
    logistics_company  VARCHAR(255) NULL,
    tracking_id VARCHAR(255) NULL,

    
    inventory_owner_id BINARY(16) NOT NULL,

    status ENUM(
        'PENDING',
        'ASSIGNED',
        'PICKED_UP',
        'IN_TRANSIT',
        'DELIVERED',
        'FAILED',
        'RETURNED'
    ) NOT NULL DEFAULT 'PENDING',

    delivery_type ENUM(
        'LOCAL',
        'COURIER',
        'INTERNATIONAL'
    ) NOT NULL DEFAULT 'LOCAL',

    estimated_delivery DATETIME NULL,
    delivered_at DATETIME NULL,

    pickup_address TEXT NULL,
    delivery_address TEXT NULL,

    notes TEXT NULL,

    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    /* FOREIGN KEYS */
    CONSTRAINT fk_delivery_order
        FOREIGN KEY (order_id) REFERENCES orders(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_delivery_customer
        FOREIGN KEY (customer_id) REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_delivery_driver
        FOREIGN KEY (driver_id) REFERENCES riders(id)
        ON DELETE SET NULL,

    CONSTRAINT fk_delivery_inventory_owner
        FOREIGN KEY (inventory_owner_id) REFERENCES users(id)
        ON DELETE CASCADE,

    /* INDEXES */
    INDEX idx_delivery_order (order_id),
    INDEX idx_delivery_customer (customer_id),
    INDEX idx_delivery_driver (driver_id),
    INDEX idx_delivery_status (status),
    INDEX idx_delivery_tracking (tracking_id)
);