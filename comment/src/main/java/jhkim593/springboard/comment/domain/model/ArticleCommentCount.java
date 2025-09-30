package jhkim593.springboard.comment.domain.model;

import jakarta.persistence.*;
import jhkim593.springboard.comment.domain.dto.CommentRegisterDto;
import jhkim593.springboard.comment.domain.dto.CommentUpdateDto;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleCommentCount {
    @Id
    private Long articleId;
    private Long commentCount;

    public static ArticleCommentCount create(Long articleId, Long commentCount) {
        ArticleCommentCount articleCommentCount = new ArticleCommentCount();
        articleCommentCount.articleId = articleId;
        articleCommentCount.commentCount = 1L;
        return articleCommentCount;
    }
    public void increase() {
        this.commentCount++;
    }
    public void decrease() {
        this.commentCount--;
    }
}