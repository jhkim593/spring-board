package jhkim593.springboard.comment.application.required;

import jhkim593.springboard.comment.domain.model.ArticleCommentCount;

public interface ArticleCommentCountRepository {
    ArticleCommentCount findById(Long id);
    ArticleCommentCount save(ArticleCommentCount articleCommentCount);
    void delete(Long id);
}
