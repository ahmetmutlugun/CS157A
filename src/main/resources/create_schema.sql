CREATE TABLE products (
                          product_id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          category VARCHAR(255),
                          price DOUBLE PRECISION,
                          stock_quantity INT
);

CREATE TABLE customers (
                           customer_id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) UNIQUE,
                           phone_number VARCHAR(20),
                           loyalty_points INT
);

CREATE TABLE sales (
                       sale_id BIGSERIAL PRIMARY KEY,
                       product_id BIGINT,
                       customer_id BIGINT,
                       date TIMESTAMP,
                       quantity INT,
                       total_amount DOUBLE PRECISION,
                       FOREIGN KEY (product_id) REFERENCES products(product_id),
                       FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);