CREATE TYPE type AS ENUM ('WEEKLY', 'MONTHLY');

CREATE TABLE loan_product
(
    id                       SERIAL NOT NULL,
    type                     type   NOT NULL,
    loan_duration_id         BIGINT NOT NULL,
    installment_frequency_id BIGINT NOT NULL,
    loan_fee_id              BIGINT NOT NULL,
    CONSTRAINT pk_loan_product PRIMARY KEY (id)
);

ALTER TABLE loan_product
    ADD CONSTRAINT FK_LOAN_PRODUCT_ON_INSTALLMENT_FREQUENCY FOREIGN KEY (installment_frequency_id) REFERENCES installment_frequency (id);

ALTER TABLE loan_product
    ADD CONSTRAINT FK_LOAN_PRODUCT_ON_LOAN_DURATION FOREIGN KEY (loan_duration_id) REFERENCES loan_duration (id);

ALTER TABLE loan_product
    ADD CONSTRAINT FK_LOAN_PRODUCT_ON_LOAN_FEE FOREIGN KEY (loan_fee_id) REFERENCES loan_fee (id);

CREATE TABLE installment_frequency
(
    id                    SERIAL  NOT NULL,
    applicable_to_monthly BOOLEAN NOT NULL,
    applicable_to_weekly  BOOLEAN NOT NULL,
    CONSTRAINT pk_installment_frequency PRIMARY KEY (id)
);
CREATE TABLE loan_duration
(
    id               SERIAL  NOT NULL,
    min_duration     INTEGER NOT NULL,
    max_duration     INTEGER NOT NULL,
    days_in_duration INTEGER NOT NULL,
    CONSTRAINT pk_loan_duration PRIMARY KEY (id)
);

CREATE TABLE loan_fee
(
    id               SERIAL           NOT NULL,
    interest_rate    DOUBLE PRECISION NOT NULL,
    service_fee_rate DOUBLE PRECISION NOT NULL,
    service_fee_cap  DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_loan_fee PRIMARY KEY (id)
);


INSERT INTO loan_product (id, type, loan_duration_id, installment_frequency_id, loan_fee_id)
VALUES (nextval('hibernate_sequence'), 'WEEKLY', 1, 1, 1), -- For Weekly
       (nextval('hibernate_sequence'), 'MONTHLY', 2, 2, 2);-- For Monthly

INSERT INTO loan_duration (id, min_duration, max_duration, days_in_duration)
VALUES (nextval('hibernate_sequence'), 1, 4, 7), -- For Weekly
       (nextval('hibernate_sequence'), 1, 12, 30); -- For Monthly

INSERT INTO installment_frequency (id, applicable_to_monthly, applicable_to_weekly)
VALUES (nextval('hibernate_sequence'), true, true), -- For Weekly
       (nextval('hibernate_sequence'), true, false); -- For Monthly

INSERT INTO loan_fee (id, interest_rate, service_fee_rate, service_fee_cap)
VALUES (nextval('hibernate_sequence'), 1.0, 0.5, 50.0), -- For Weekly
       (nextval('hibernate_sequence'), 4.0, 0.5, 100.0); -- For Monthly
