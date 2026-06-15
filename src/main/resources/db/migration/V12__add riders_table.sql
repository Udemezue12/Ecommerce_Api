CREATE TABLE riders (
    id BINARY(16) PRIMARY KEY,

    

    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(30) NOT NULL UNIQUE,

    vehicle_type VARCHAR(50) NULL,
    vehicle_plate_number VARCHAR(50) NULL,

    is_available BOOLEAN NOT NULL DEFAULT TRUE,

   
    resource_type VARCHAR(50) NULL,
    image_hash VARCHAR(255) NULL,
    public_id VARCHAR(255) NULL,
    image_url TEXT NULL,

    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    

   
    INDEX idx_riders_available (is_available)
);