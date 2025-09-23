package jhkim593.springboard.common.core.article.application.required.repository;

import jhkim593.springboard.common.core.article.domain.model.BoardArticleCount;

public interface BoardArticleCountRepository {
    BoardArticleCount save(BoardArticleCount boardArticleCount);
    BoardArticleCount findById(Long boardId);
}
