package jhkim593.springboard.article.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jhkim593.springboard.article.domain.dto.ArticleRegisterDto;
import jhkim593.springboard.article.domain.dto.ArticleUpdateDto;
import jhkim593.springboard.common.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.event.payload.ArticleDeletedEventPayload;
import jhkim593.springboard.common.event.payload.ArticleRegisteredEventPayload;
import jhkim593.springboard.common.event.payload.ArticleUpdatedEventPayload;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Article {
    @Id
    private Long articleId;
    private String title;
    private String content;
    private Long boardId;
    private Long writerId;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Article create(Long id, ArticleRegisterDto request){
        return Article.builder()
                .articleId(id)
                .title(request.getTitle())
                .content(request.getContent())
                .boardId(request.getBoardId())
                .writerId(request.getWriterId())
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }

    public ArticleRegisteredEventPayload createRegisteredEventPayload(Long boardArticleCount){
        return ArticleRegisteredEventPayload.builder()
                .articleId(this.getArticleId())
                .title(this.getTitle())
                .content(this.getContent())
                .boardId(this.getBoardId())
                .writerId(this.getWriterId())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .boardArticleCount(boardArticleCount)
                .build();
    }
    public ArticleDeletedEventPayload createDeletedEventPayload(Long boardArticleCount){
        return ArticleDeletedEventPayload.builder()
                .articleId(this.getArticleId())
                .boardId(this.getBoardId())
                .writerId(this.getWriterId())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .boardArticleCount(boardArticleCount)
                .build();
    }
    public ArticleUpdatedEventPayload createUpdatedEventPayload(){
        return ArticleUpdatedEventPayload.builder()
                .articleId(this.getArticleId())
                .title(this.getTitle())
                .content(this.getContent())
                .boardId(this.getBoardId())
                .writerId(this.getWriterId())
                .modifiedAt(this.getModifiedAt())
                .build();
    }
    public ArticleDetailDto createDetailDto(){
        return ArticleDetailDto.builder()
                .articleId(this.getArticleId())
                .title(this.getTitle())
                .content(this.getContent())
                .boardId(this.getBoardId())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();
    }

    public void update(ArticleUpdateDto request){
        this.title = request.getTitle();
        this.content = request.getContent();
        this.modifiedAt = LocalDateTime.now();
    }
    public void delete(){
        this.deleted = true;
    }
}
