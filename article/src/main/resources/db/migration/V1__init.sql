CREATE TABLE article (
    article_id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    board_id BIGINT NOT NULL,
    writer_id BIGINT NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP NOT NULL
);

-- CREATE INDEX idx_article_board_id ON article(board_id);
-- CREATE INDEX idx_article_writer_id ON article(writer_id);
-- CREATE INDEX idx_article_created_at ON article(created_at);

CREATE TABLE article_event (
    id BIGINT PRIMARY KEY,
    event_type VARCHAR(50) NOT NULL,
    article_id BIGINT NOT NULL,
    payload TEXT NOT NULL,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL
);

-- CREATE INDEX idx_article_event_article_id ON article_event(article_id);
-- CREATE INDEX idx_article_event_published ON article_event(published);
-- CREATE INDEX idx_article_event_created_at ON article_event(created_at);