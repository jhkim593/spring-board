package jhkim593.springboard.comment.adapter.api;

import jhkim593.springboard.comment.application.provided.CommentFinder;
import jhkim593.springboard.comment.application.provided.CommentUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentUpdater commentUpdater;
    private final CommentFinder commentFinder;

}
