package jhkim593.springboard.common;

import jhkim593.springboard.articleread.domain.ArticleRead;

import java.time.LocalDateTime;

public class ArticleReadDataFactory {

    private static final Long DEFAULT_ARTICLE_ID = 1L;
    private static final String DEFAULT_TITLE = "title";
    private static final String DEFAULT_CONTENT = "content";
    private static final Long DEFAULT_BOARD_ID = 1L;
    private static final Long DEFAULT_WRITER_ID = 1L;
    private static final Long DEFAULT_COMMENT_COUNT = 0L;
    private static final Long DEFAULT_LIKE_COUNT = 0L;

    private static ArticleRead.ArticleReadBuilder defaultArticleReadBuilder() {
        return ArticleRead.builder()
                .articleId(DEFAULT_ARTICLE_ID)
                .title(DEFAULT_TITLE)
                .content(DEFAULT_CONTENT)
                .boardId(DEFAULT_BOARD_ID)
                .writerId(DEFAULT_WRITER_ID)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .articleCommentCount(DEFAULT_COMMENT_COUNT)
                .articleLikeCount(DEFAULT_LIKE_COUNT);
    }
    public static ArticleRead createTestArticleRead(Long boardId, Long articleId) {
        return defaultArticleReadBuilder()
                .boardId(boardId)
                .articleId(articleId)
                .build();
    }
}