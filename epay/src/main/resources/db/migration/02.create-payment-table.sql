CREATE TABLE IF NOT EXISTS epay.payment (
    id        bigserial PRIMARY KEY,
    full_name text NOT NULL,
    state     text NOT NULL,
    payment_id bigint
);