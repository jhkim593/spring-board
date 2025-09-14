package jhkim593.springboard.article.application.required;

import jhkim593.springboard.article.application.provided.BoardArticleCountUpdater;
import jhkim593.springboard.article.application.required.repository.BoardArticleCountRepository;
import jhkim593.springboard.article.domain.BoardArticleCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardArticleCountUpdateService implements BoardArticleCountUpdater {
    private final BoardArticleCountRepository boardArticleCountRepository;

    @Transactional
    @Override
    public Long increase(Long boardId) {
        Long count = boardArticleCountRepository.increase(boardId);
        if (count == 0) {
            BoardArticleCount boardArticleCount = boardArticleCountRepository.save(BoardArticleCount.create(boardId));
            count = boardArticleCount.getArticleCount();
        }
        return count;
    }

    @Transactional
    @Override
    public Long decrease(Long boardId) {
        return boardArticleCountRepository.decrease(boardId);
    }
}
