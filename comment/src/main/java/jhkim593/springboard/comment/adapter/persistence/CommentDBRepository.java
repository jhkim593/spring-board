package jhkim593.springboard.comment.adapter.persistence;

import jhkim593.springboard.comment.adapter.persistence.jpa.CommentJpaRepository;
import jhkim593.springboard.comment.application.required.CommentRepository;
import jhkim593.springboard.comment.domain.error.ErrorCode;
import jhkim593.springboard.comment.domain.model.Comment;
import jhkim593.springboard.common.core.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentDBRepository implements CommentRepository {
    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        return commentJpaRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }
}
