package jhkim593.springboard.article.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jhkim593.springboard.article.domain.dto.ArticleRegisterDto;
import jhkim593.springboard.article.domain.dto.ArticleUpdateDto;
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

    public static Article create(ArticleRegisterDto request){
        return Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .boardId(request.getBoardId())
                .writerId(request.getWriterId())
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
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
