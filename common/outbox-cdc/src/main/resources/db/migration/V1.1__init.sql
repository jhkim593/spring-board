CREATE TABLE outbox_event (
    id BIGINT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    aggregate_id BIGINT NOT NULL,
    payload TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE PUBLICATION outbox_publication
FOR TABLE outbox_event
WITH (publish = 'insert');