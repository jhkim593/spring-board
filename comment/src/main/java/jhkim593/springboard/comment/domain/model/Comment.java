package jhkim593.springboard.comment.domain.model;

import jakarta.persistence.*;
import jhkim593.springboard.comment.domain.dto.CommentRegisterDto;
import jhkim593.springboard.comment.domain.dto.CommentUpdateDto;
import jhkim593.springboard.common.core.dto.comment.CommentDetailDto;
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

    public static Comment create(Long commentId, CommentRegisterDto registerDto, Long parentCommentId) {
        return Comment.builder()
                .commentId(commentId)
                .content(registerDto.getContent())
                .parentCommentId(parentCommentId)
                .articleId(registerDto.getArticleId())
                .writerId(registerDto.getWriterId())
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public CommentDetailDto createDetailDto() {
        return CommentDetailDto.builder()
                .commentId(commentId)
                .content(content)
                .parentCommentId(parentCommentId)
                .articleId(articleId)
                .writerId(writerId)
                .isDeleted(isDeleted)
                .createdAt(createdAt)
                .build();
    }

    public void update(CommentUpdateDto updateDto) {
        this.content = updateDto.getContent();
    }

    public boolean isNotRoot() {
        return parentCommentId != null;
    }

    public void delete() {
        isDeleted = true;
    }
}