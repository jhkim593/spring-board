package jhkim593.springboard.articleread.domain;

import jhkim593.springboard.articleread.domain.dto.ArticleReadDetailDto;
import jhkim593.springboard.common.core.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.core.event.payload.ArticleRegisteredEventPayload;
import jhkim593.springboard.common.core.event.payload.ArticleUpdatedEventPayload;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleRead {
    private Long articleId;
    private String title;
    private String content;
    private Long boardId;
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long articleCommentCount = 0L;
    private Long articleLikeCount = 0L;

    public static ArticleRead create(ArticleRegisteredEventPayload payload) {
        ArticleRead articleRead = new ArticleRead();
        articleRead.articleId = payload.getArticleId();
        articleRead.title = payload.getTitle();
        articleRead.content = payload.getContent();
        articleRead.boardId = payload.getBoardId();
        articleRead.writerId = payload.getWriterId();
        articleRead.createdAt = payload.getCreatedAt();
        articleRead.modifiedAt = payload.getModifiedAt();
        return articleRead;
    }

    public static List<ArticleReadDetailDto> createDetailDtos(List<ArticleRead> articleReads) {
        return articleReads.stream()
                .map(ArticleRead::createDetailDto)
                .toList();
    }
    public static ArticleRead create(ArticleDetailDto articleDetailDto, Long commentCount, Long likeCount) {
        ArticleRead articleRead = new ArticleRead();
        articleRead.articleId = articleDetailDto.getArticleId();
        articleRead.title = articleDetailDto.getTitle();
        articleRead.content = articleDetailDto.getContent();
        articleRead.boardId = articleDetailDto.getBoardId();
        articleRead.writerId = articleDetailDto.getWriterId();
        articleRead.createdAt = articleDetailDto.getCreatedAt();
        articleRead.modifiedAt = articleDetailDto.getModifiedAt();
        articleRead.articleCommentCount = commentCount;
        articleRead.articleLikeCount = likeCount;
        return articleRead;
    }

    public static ArticleRead create(ArticleUpdatedEventPayload payload) {
        ArticleRead articleRead = new ArticleRead();
        articleRead.articleId = payload.getArticleId();
        articleRead.title = payload.getTitle();
        articleRead.content = payload.getContent();
        articleRead.boardId = payload.getBoardId();
        articleRead.writerId = payload.getWriterId();
        articleRead.modifiedAt = payload.getModifiedAt();
        return articleRead;
    }

    public ArticleReadDetailDto createDetailDto() {
        return ArticleReadDetailDto.builder()
                .articleId(this.articleId)
                .title(this.title)
                .content(this.content)
                .boardId(this.boardId)
                .writerId(this.writerId)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .articleCommentCount(this.articleCommentCount)
                .articleLikeCount(this.articleLikeCount)
                .build();
    }
}

