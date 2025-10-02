package jhkim593.springboard.comment.application.service;

import jhkim593.springboard.comment.application.provided.CommentFinder;
import jhkim593.springboard.comment.application.required.CommentRepository;
import jhkim593.springboard.common.core.dto.comment.CommentDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentQueryService implements CommentFinder {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentDetailDto> find(Long articleId, Long parentCommentId, Long lasCommentId) {
        return commentRepository.find(articleId, parentCommentId, lasCommentId, 50);
    }
}
