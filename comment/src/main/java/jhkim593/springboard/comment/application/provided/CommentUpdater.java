package jhkim593.springboard.comment.application.provided;

import jhkim593.springboard.comment.domain.dto.CommentRegisterDto;
import jhkim593.springboard.comment.domain.dto.CommentUpdateDto;
import jhkim593.springboard.common.core.dto.comment.CommentDetailDto;

public interface CommentUpdater {
    CommentDetailDto register(CommentRegisterDto registerDto);
    CommentDetailDto update(Long id, CommentUpdateDto updateDto);
    void delete(Long id);
}
