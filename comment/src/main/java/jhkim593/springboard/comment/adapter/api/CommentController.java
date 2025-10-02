package jhkim593.springboard.comment.adapter.api;

import jhkim593.springboard.comment.application.provided.CommentFinder;
import jhkim593.springboard.comment.application.provided.CommentUpdater;
import jhkim593.springboard.comment.domain.dto.CommentRegisterDto;
import jhkim593.springboard.comment.domain.dto.CommentUpdateDto;
import jhkim593.springboard.common.core.dto.comment.CommentDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentUpdater commentUpdater;
    private final CommentFinder commentFinder;

    @GetMapping("/api/v1/comments")
    public ResponseEntity<List<CommentDetailDto>> getComments(
            @RequestParam Long articleId,
            @RequestParam(required = false) Long parentCommentId,
            @RequestParam(required = false) Long lastCommentId) {
        return ResponseEntity.ok(commentFinder.find(articleId, parentCommentId, lastCommentId));
    }

    @PostMapping("/api/v1/comments")
    public ResponseEntity<CommentDetailDto> create(@RequestBody CommentRegisterDto request) {
        return ResponseEntity.ok(commentUpdater.register(request));
    }

    @PutMapping("/api/v1/comments/{id}")
    public ResponseEntity<CommentDetailDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentUpdateDto request) {
        commentUpdater.update(id, request);
        return ResponseEntity.ok(commentUpdater.update(id, request));
    }

    @DeleteMapping("/api/v1/comments/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id) {
        commentUpdater.delete(id);
        return ResponseEntity.ok().build();
    }
}
