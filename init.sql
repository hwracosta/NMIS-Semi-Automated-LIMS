CREATE TABLE CLIENT_register (
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    representative_name VARCHAR(255) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    company_name VARCHAR(255) NOT NULL
    client_id SERIAL PRIMARY KEY,
);

CREATE TABLE STAFF_register (
    staff_id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    staff_type VARCHAR(50) NOT NULL
);


ALTER TABLE CLIENT_register ADD COLUMN reset_token VARCHAR(255), ADD COLUMN token_expiry TIMESTAMP;
ALTER TABLE STAFF_register ADD COLUMN reset_token VARCHAR(255), ADD COLUMN token_expiry TIMESTAMP;
