CREATE TABLE CLIENT_register (
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    representative_name VARCHAR(255) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    client_id SERIAL PRIMARY KEY,
);

CREATE TABLE STAFF_register (
    staff_id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    staff_type VARCHAR(50) NOT NULL
);

CREATE TABLE client_reqform (
    client_reqid SERIAL PRIMARY KEY,
    address VARCHAR (100) NOT NULL,
    or_no VARCHAR(100) NOT NULL,
    ld_no VARCHAR(100) NOT NULL,
    client_sample_code VARCHAR(100) NOT NULL,
    sample_details VARCHAR(255) NOT NULL,
    sample_source VARCHAR(255) NOT NULL,
    production_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    sampling_date DATE NOT NULL,
    weight_grams INT NOT NULL,
    purpose_test VARCHAR(255) NOT NULL,
    microbio_tests VARCHAR(500),
    molec_tests VARCHAR(500),
    chem_tests VARCHAR(500),
    releasing_results VARCHAR(100) NOT NULL
);

ALTER TABLE client_register
ADD COLUMN client_classif VARCHAR(100) NOT NULL,
ADD COLUMN lto_no VARCHAR(100);
