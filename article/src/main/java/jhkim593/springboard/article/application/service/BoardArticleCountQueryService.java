package jhkim593.springboard.article.application.service;

import jhkim593.springboard.article.application.provided.BoardArticleCountFinder;
import jhkim593.springboard.article.application.required.repository.BoardArticleCountRepository;
import jhkim593.springboard.article.domain.model.BoardArticleCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardArticleCountQueryService implements BoardArticleCountFinder {
    private final BoardArticleCountRepository boardArticleCountRepository;

    @Override
    public Long count(Long boardId) {
        BoardArticleCount boardArticleCount = boardArticleCountRepository.findById(boardId);
        if (boardArticleCount == null) return 0L;
        return boardArticleCount.getArticleCount();
    }
}
