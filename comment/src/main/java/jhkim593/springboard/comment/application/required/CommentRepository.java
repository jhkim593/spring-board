package jhkim593.springboard.comment.application.required;


import jhkim593.springboard.comment.domain.model.Comment;

public interface CommentRepository {
    Comment save(Comment registerDto);
    Comment findById(Long id);
}
