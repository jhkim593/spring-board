package jhkim593.springboard.common.core.dto.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDetailDto {
    private Long commentId;
    private String content;
    private Long parentCommentId;
    private Long articleId;
    private Long writerId;
    private Boolean isDeleted;
    private LocalDateTime createdAt;

    public CommentDetailDto(Long commentId, String content, Long parentCommentId, Long articleId, Long writerId, Boolean isDeleted, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.content = content;
        this.parentCommentId = parentCommentId;
        this.articleId = articleId;
        this.writerId = writerId;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }
}
