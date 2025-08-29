package jhkim593.springboard.article.domain.dto;

import jhkim593.springboard.article.domain.Article;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class ArticleDetailDto {
    private Long articleId;
    private String title;
    private String content;
    private Long boardId;
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ArticleDetailDto create(Article article) {
        return ArticleDetailDto.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .content(article.getContent())
                .boardId(article.getBoardId())
                .createdAt(article.getCreatedAt())
                .modifiedAt(article.getModifiedAt())
                .build();
    }
}
