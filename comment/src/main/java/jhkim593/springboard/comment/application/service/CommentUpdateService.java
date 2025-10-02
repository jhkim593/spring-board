package jhkim593.springboard.comment.application.service;

import jakarta.transaction.Transactional;
import jhkim593.springboard.comment.application.provided.CommentUpdater;
import jhkim593.springboard.comment.application.required.CommentRepository;
import jhkim593.springboard.comment.domain.dto.CommentRegisterDto;
import jhkim593.springboard.comment.domain.dto.CommentUpdateDto;
import jhkim593.springboard.comment.domain.error.ErrorCode;
import jhkim593.springboard.comment.domain.model.Comment;
import jhkim593.springboard.common.core.dto.comment.CommentDetailDto;
import jhkim593.springboard.common.core.error.CustomException;
import jhkim593.springboard.common.core.snowflake.DBIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentUpdateService implements CommentUpdater {
    private final CommentRepository commentRepository;
    private final DBIdGenerator idGenerator;

    @Override
    public CommentDetailDto register(CommentRegisterDto registerDto) {
        long id = idGenerator.getId();
        Long parentCommentId = registerDto.getParentCommentId();

        if(parentCommentId == null) {
            Comment comment = Comment.create(id, registerDto, id);
            return commentRepository.save(comment).createDetailDto();
        }

        Comment parent = commentRepository.findById(parentCommentId);
        if(parent.isNotRoot()){
            throw new CustomException(ErrorCode.COMMENT_MAX_DEPTH_EXCEED);
        }

        Comment comment = Comment.create(id, registerDto, parentCommentId);
        commentRepository.save(comment).createDetailDto();
    }

    @Override
    public CommentDetailDto update(Long id, CommentUpdateDto updateDto) {
        Comment comment = commentRepository.findById(id);
        comment.update(updateDto);
        return commentRepository.save(comment).createDetailDto();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id);
        if(comment.isNotRoot()) {
            commentRepository.delete(id);
            return;
        }
        if(commentRepository.hasChildComment(comment.getArticleId(), comment.getCommentId())) {
            comment.delete();
            commentRepository.save(comment);
            return;
        }
        commentRepository.delete(id);
    }
}
