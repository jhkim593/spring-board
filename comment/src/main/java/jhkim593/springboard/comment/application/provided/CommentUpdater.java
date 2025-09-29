package jhkim593.springboard.comment.application.provided;

import jhkim593.springboard.comment.domain.dto.CommentRegisterDto;
import jhkim593.springboard.comment.domain.dto.CommentUpdateDto;

public interface CommentUpdater {
    void register(CommentRegisterDto registerDto);
    void update(Long id, CommentUpdateDto updateDto);
    void delete(Long id);
}
