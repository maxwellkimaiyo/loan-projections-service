CREATE TABLE installment_frequency
(
    id                    SERIAL PRIMARY KEY,
    applicable_to_monthly BOOLEAN NOT NULL,
    applicable_to_weekly  BOOLEAN NOT NULL
);
CREATE TABLE loan_duration
(
    id               SERIAL PRIMARY KEY,
    min_duration     INTEGER NOT NULL,
    max_duration     INTEGER NOT NULL,
    days_in_duration INTEGER NOT NULL
);

CREATE TABLE loan_fee
(
    id               SERIAL PRIMARY KEY,
    interest_rate    DOUBLE PRECISION,
    service_fee_rate DOUBLE PRECISION,
    service_fee_cap  DOUBLE PRECISION
);


CREATE TABLE loan_product
(
    id                       SERIAL PRIMARY KEY,
    type                     VARCHAR(255) NOT NULL,
    loan_duration_id         BIGINT       NOT NULL,
    installment_frequency_id BIGINT       NOT NULL,
    loan_fee_id              BIGINT       NOT NULL
);

ALTER TABLE loan_product
    ADD CONSTRAINT FK_LOAN_PRODUCT_ON_INSTALLMENT_FREQUENCY FOREIGN KEY (installment_frequency_id) REFERENCES installment_frequency (id);

ALTER TABLE loan_product
    ADD CONSTRAINT FK_LOAN_PRODUCT_ON_LOAN_DURATION FOREIGN KEY (loan_duration_id) REFERENCES loan_duration (id);

ALTER TABLE loan_product
    ADD CONSTRAINT FK_LOAN_PRODUCT_ON_LOAN_FEE FOREIGN KEY (loan_fee_id) REFERENCES loan_fee (id);


INSERT INTO loan_duration (id, min_duration, max_duration, days_in_duration)
VALUES (1, 1, 4, 7),
       (2, 1, 12, 30);


INSERT INTO installment_frequency (id, applicable_to_monthly, applicable_to_weekly)
VALUES (1, true, true),
       (2, true, false);

INSERT INTO loan_fee (id, interest_rate, service_fee_rate, service_fee_cap)
VALUES (1, 1.0, 0.5, 50.0),
       (2, 4.0, 0.5, 100.0);

INSERT INTO loan_product (id, type, loan_duration_id, installment_frequency_id, loan_fee_id)
VALUES (1, 'WEEKLY', 1, 1, 1),
       (2, 'MONTHLY', 2, 2, 2);
