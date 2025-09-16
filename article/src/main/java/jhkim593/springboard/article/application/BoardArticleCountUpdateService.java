package jhkim593.springboard.article.application;

import jhkim593.springboard.article.application.provided.BoardArticleCountUpdater;
import jhkim593.springboard.article.application.required.repository.BoardArticleCountRepository;
import jhkim593.springboard.article.domain.BoardArticleCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardArticleCountUpdateService implements BoardArticleCountUpdater {
    private final BoardArticleCountRepository boardArticleCountRepository;

    @Override
    public Long increase(Long boardId) {
        BoardArticleCount boardArticleCount = boardArticleCountRepository.findById(boardId).orElse(null);
        if(boardArticleCount == null) {
            boardArticleCount = BoardArticleCount.create(boardId);
            boardArticleCountRepository.save(boardArticleCount);
            return 1L;
        }
        boardArticleCount.increase();
        boardArticleCountRepository.save(boardArticleCount);
        return boardArticleCount.getArticleCount();
    }

    @Override
    public Long decrease(Long boardId) {
        BoardArticleCount boardArticleCount = boardArticleCountRepository.findById(boardId).orElse(null);
        if(boardArticleCount == null) {
            return 0L;
        }
        boardArticleCount.decrease();
        boardArticleCountRepository.save(boardArticleCount);
        return boardArticleCount.getArticleCount();
    }
}
