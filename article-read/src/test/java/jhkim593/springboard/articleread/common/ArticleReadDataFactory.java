package jhkim593.springboard.articleread.common;

import jhkim593.springboard.articleread.domain.ArticleRead;
import jhkim593.springboard.common.dto.article.ArticleDetailDto;

import java.time.LocalDateTime;

public class ArticleReadDataFactory {

    private static final Long DEFAULT_ARTICLE_ID = 1L;
    private static final String DEFAULT_TITLE = "title";
    private static final String DEFAULT_CONTENT = "content";
    private static final Long DEFAULT_BOARD_ID = 1L;
    private static final Long DEFAULT_WRITER_ID = 1L;
    private static final Long DEFAULT_COMMENT_COUNT = 0L;
    private static final Long DEFAULT_LIKE_COUNT = 0L;

    private static ArticleDetailDto.ArticleDetailDtoBuilder defaultArticleDetailDtoBuilder() {
        return ArticleDetailDto.builder()
                .articleId(DEFAULT_ARTICLE_ID)
                .title(DEFAULT_TITLE)
                .content(DEFAULT_CONTENT)
                .boardId(DEFAULT_BOARD_ID)
                .writerId(DEFAULT_WRITER_ID)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now());
    }

    public static ArticleRead createTestArticleRead() {
        return ArticleRead.create(defaultArticleDetailDtoBuilder().build(), DEFAULT_COMMENT_COUNT, DEFAULT_LIKE_COUNT);
    }

    public static ArticleRead createTestArticleRead(Long boardId, Long articleId) {
        ArticleDetailDto articleDetailDto = defaultArticleDetailDtoBuilder()
                .boardId(boardId)
                .articleId(articleId)
                .build();

        return ArticleRead.create(articleDetailDto, DEFAULT_COMMENT_COUNT, DEFAULT_LIKE_COUNT);
    }

    public static ArticleRead createTestArticleRead(Long boardId, Long articleId, Long commentCount, Long likeCount) {
        ArticleDetailDto articleDetailDto = defaultArticleDetailDtoBuilder()
                .boardId(boardId)
                .articleId(articleId)
                .build();

        return ArticleRead.create(articleDetailDto, commentCount, likeCount);
    }

    public static ArticleDetailDto createTestArticleDetailDto() {
        return defaultArticleDetailDtoBuilder().build();
    }

    public static ArticleDetailDto createTestArticleDetailDto(Long boardId, Long articleId) {
        return defaultArticleDetailDtoBuilder()
                .boardId(boardId)
                .articleId(articleId)
                .build();
    }
}