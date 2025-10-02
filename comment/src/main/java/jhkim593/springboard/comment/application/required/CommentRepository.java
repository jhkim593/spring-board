package jhkim593.springboard.comment.application.required;


import jhkim593.springboard.comment.domain.model.Comment;
import jhkim593.springboard.common.core.dto.comment.CommentDetailDto;

import java.util.List;

public interface CommentRepository {
    Comment save(Comment registerDto);
    Comment findById(Long id);
    void delete(Long id);
    boolean hasChildComment(Long articleId, Long parentCommentId);
    List<CommentDetailDto> find(Long articleId, Long parentCommentId, Long lastCommentId, int limit);
}
