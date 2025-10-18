CREATE TABLE outbox_event (
    id BIGINT PRIMARY KEY,
    event_type VARCHAR(50) NOT NULL,
    aggregate_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL
);

CREATE PUBLICATION outbox_publication
FOR TABLE outbox_event
WITH (publish = 'insert');

-- CREATE INDEX idx_article_event_article_id ON article_event(article_id);
-- CREATE INDEX idx_article_event_published ON article_event(published);
-- CREATE INDEX idx_article_event_created_at ON article_event(created_at);