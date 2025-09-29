package jhkim593.springboard.comment.application.service;

import jhkim593.springboard.comment.application.provided.CommentUpdater;
import jhkim593.springboard.comment.application.required.CommentRepository;
import jhkim593.springboard.comment.domain.dto.CommentRegisterDto;
import jhkim593.springboard.comment.domain.dto.CommentUpdateDto;
import jhkim593.springboard.comment.domain.model.Comment;
import jhkim593.springboard.common.core.snowflake.DBIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentUpdateService implements CommentUpdater {
    private final CommentRepository commentRepository;
    private final DBIdGenerator idGenerator;

    @Override
    public void register(CommentRegisterDto registerDto) {
        long id = idGenerator.getId();
        Long parentCommentId = registerDto.getParentCommentId();

        Comment parent = registerDto.getParentCommentId() == null ? null : commentRepository.findById(parentCommentId);
        Comment comment = Comment.create(id, registerDto, parent);
        commentRepository.save(comment);
    }

    @Override
    public void update(Long id, CommentUpdateDto updateDto) {
        Comment comment = commentRepository.findById(id);
        comment.update(updateDto);
        commentRepository.save(comment);
    }

    @Override
    public void delete(Long id) {

    }
}
