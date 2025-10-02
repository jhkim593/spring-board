package jhkim593.springboard.comment.domain.dto;

import lombok.Getter;

@Getter
public class CommentRegisterDto {
    private String content;
    private Long articleId;
    private Long parentCommentId;
    private Long writerId;
}
