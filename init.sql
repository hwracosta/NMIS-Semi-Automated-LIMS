CREATE TABLE CLIENT_register (
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    representative_name VARCHAR(255) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    company_name VARCHAR(255) NOT NULL
);

CREATE TABLE STAFF_register (
    email VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    staff_type VARCHAR(50) NOT NULL
);
