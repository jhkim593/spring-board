package jhkim593.springboard.common.core.article.adapter.persistence;

import jhkim593.springboard.common.core.article.adapter.persistence.jpa.BoardArticleCountJpaRepository;
import jhkim593.springboard.common.core.article.application.required.repository.BoardArticleCountRepository;
import jhkim593.springboard.common.core.article.domain.model.BoardArticleCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardArticleCountDBRepository implements BoardArticleCountRepository{
    private final BoardArticleCountJpaRepository boardArticleCountJpaRepository;
    @Override
    public BoardArticleCount save(BoardArticleCount boardArticleCount) {
        return boardArticleCountJpaRepository.save(boardArticleCount);
    }

    @Override
    public BoardArticleCount findById(Long boardId) {
        return boardArticleCountJpaRepository.findById(boardId).orElse(null);
    }
}
