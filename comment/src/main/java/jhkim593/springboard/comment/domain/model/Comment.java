package jhkim593.springboard.comment.domain.model;

import jakarta.persistence.*;
import jhkim593.springboard.comment.domain.dto.CommentRegisterDto;
import jhkim593.springboard.comment.domain.dto.CommentUpdateDto;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parent;

    private Long articleId;
    private Long writerId;
    private Boolean isDeleted;
    private LocalDateTime createdAt;

    public static Comment create(Long commentId, CommentRegisterDto registerDto, Comment parentComment) {
        return Comment.builder()
                .commentId(commentId)
                .content(registerDto.getContent())
                .parent(parentComment)
                .articleId(registerDto.getArticleId())
                .writerId(registerDto.getWriterId())
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void update(CommentUpdateDto updateDto) {
        this.content = updateDto.getContent();
    }

    public boolean isRoot() {
        return parent == null;
    }

    public void delete() {
        isDeleted = true;
    }


}