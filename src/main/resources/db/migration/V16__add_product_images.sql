ALTER TABLE products
    DROP COLUMN resource_type,
    DROP COLUMN image_hash,
    DROP COLUMN public_id,
    DROP COLUMN image_url;


CREATE TABLE product_images
(
    id            BINARY(16)    NOT NULL,
    product_id    BINARY(16)    NOT NULL,
    image_url     VARCHAR(2048) NOT NULL,
    image_hash    VARCHAR(255),
    resource_type VARCHAR(50),
    public_id     VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_product_images_product FOREIGN KEY (product_id)
        REFERENCES products (id) ON DELETE CASCADE
)