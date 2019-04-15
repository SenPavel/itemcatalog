CREATE SCHEMA IF NOT EXISTS item_catalog;

CREATE TABLE IF NOT EXISTS item_catalog."currency"
(
    id   BIGSERIAL NOT NULL
        CONSTRAINT "currency_pkey" PRIMARY KEY,
    code text NOT NULL,
    rate NUMERIC(9,4)      NOT NULL
);

CREATE TABLE IF NOT EXISTS item_catalog."item"
(
    id BIGSERIAL NOT NULL
        CONSTRAINT "item_pkey" PRIMARY KEY,
    name text NOT NULL,
    price NUMERIC(9, 2) NOT NULL
)