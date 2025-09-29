CREATE TABLE comment (
    comment_id BIGINT NOT NULL PRIMARY KEY,
    content VARCHAR(3000) NOT NULL,
    parent_comment_id BIGINT NOT NULL,
    article_id BIGINT NOT NULL,
    writer_id BIGINT NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL
);

-- CREATE INDEX idx_comment_article_id ON comment(article_id);
-- CREATE INDEX idx_comment_parent_comment_id ON comment(parent_comment_id);
-- CREATE INDEX idx_comment_writer_id ON comment(writer_id);