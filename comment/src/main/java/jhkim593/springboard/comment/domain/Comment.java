package jhkim593.springboard.comment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Comment {
    @Id
    private Long commentId;
    private String content;
    private Long parentCommentId;
    private Long articleId;
    private Long writerId;
    private Boolean isDeleted;
    private LocalDateTime createdAt;

    public static Comment create(Long commentId, String content, Long parentCommentId, Long articleId, Long writerId) {
        return Comment.builder()
                .commentId(commentId)
                .content(content)
                .parentCommentId(parentCommentId == null ? commentId : parentCommentId)
                .articleId(articleId)
                .writerId(writerId)
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public boolean isRoot() {
        return parentCommentId.longValue() == commentId;
    }

    public void delete() {
        isDeleted = true;
    }
}