package jhkim593.springboard.comment.application.provided;

import jhkim593.springboard.common.core.dto.comment.CommentDetailDto;

import java.util.List;

public interface CommentFinder {
    List<CommentDetailDto> find(Long articleId, Long parentCommentId, Long lasCommentId);
}
