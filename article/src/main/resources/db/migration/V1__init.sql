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

CREATE TABLE board_article_count (
    board_id BIGINT PRIMARY KEY,
    article_count BIGINT NOT NULL
);

-- CREATE INDEX idx_board_article_count_board_id ON board_article_count(board_id);