package jhkim593.springboard.article.common;

import jhkim593.springboard.article.domain.Article;
import jhkim593.springboard.article.domain.dto.ArticleRegisterDto;

import java.time.LocalDateTime;

public class ArticleDataFactory {

    private static final Long DEFAULT_ARTICLE_ID = 1L;
    private static final String DEFAULT_TITLE = "test title";
    private static final String DEFAULT_CONTENT = "test content";
    private static final Long DEFAULT_BOARD_ID = 1L;
    private static final Long DEFAULT_WRITER_ID = 1L;
    private static final boolean DEFAULT_DELETED = false;

    private static Article.ArticleBuilder defaultArticleBuilder() {
        return Article.builder()
                .articleId(DEFAULT_ARTICLE_ID)
                .title(DEFAULT_TITLE)
                .content(DEFAULT_CONTENT)
                .boardId(DEFAULT_BOARD_ID)
                .writerId(DEFAULT_WRITER_ID)
                .deleted(DEFAULT_DELETED)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now());
    }
    private static ArticleRegisterDto.ArticleRegisterDtoBuilder defaultArticleRegisterDtoBuilder() {
        return ArticleRegisterDto.builder()
                .title(DEFAULT_TITLE)
                .content(DEFAULT_CONTENT)
                .boardId(DEFAULT_BOARD_ID)
                .writerId(DEFAULT_WRITER_ID);
    }


    public static Article createTestArticle() {
        return defaultArticleBuilder().build();
    }

    public static Article createTestArticle(Long boardId, Long articleId) {
        return defaultArticleBuilder()
                .boardId(boardId)
                .articleId(articleId)
                .build();
    }

    public static Article createTestArticle(Long boardId, Long articleId, boolean deleted) {
        return defaultArticleBuilder()
                .boardId(boardId)
                .articleId(articleId)
                .deleted(deleted)
                .build();
    }
    public static ArticleRegisterDto createTestArticleRegisterDto() {
        return defaultArticleRegisterDtoBuilder()
                .build();
    }

    public static ArticleRegisterDto createTestArticleRegisterDto(Long boardId) {
        return defaultArticleRegisterDtoBuilder()
                .boardId(boardId)
                .build();
    }
}
