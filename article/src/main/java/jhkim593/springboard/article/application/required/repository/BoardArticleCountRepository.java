package jhkim593.springboard.article.application.required.repository;

import jhkim593.springboard.article.domain.model.BoardArticleCount;
import org.springframework.data.repository.Repository;

import java.util.Optional;
public interface BoardArticleCountRepository {
    BoardArticleCount save(BoardArticleCount boardArticleCount);
    BoardArticleCount findById(Long boardId);
}
