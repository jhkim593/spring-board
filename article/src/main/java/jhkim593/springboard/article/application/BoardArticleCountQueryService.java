package jhkim593.springboard.article.application;

import jhkim593.springboard.article.application.provided.BoardArticleCountFinder;
import jhkim593.springboard.article.application.required.repository.BoardArticleCountRepository;
import jhkim593.springboard.article.domain.BoardArticleCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardArticleCountQueryService implements BoardArticleCountFinder {
    private final BoardArticleCountRepository boardArticleCountRepository;

    @Override
    public Long count(Long boardId) {
        return boardArticleCountRepository.findById(boardId)
                .map(BoardArticleCount::getArticleCount)
                .orElse(0L);
    }
}
