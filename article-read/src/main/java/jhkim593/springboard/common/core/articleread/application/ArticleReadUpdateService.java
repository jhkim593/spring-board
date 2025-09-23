package jhkim593.springboard.common.core.articleread.application;

import jhkim593.springboard.common.core.articleread.application.provided.ArticleReadUpdater;
import jhkim593.springboard.common.core.articleread.application.required.repository.ArticleIdRepository;
import jhkim593.springboard.common.core.articleread.application.required.repository.ArticleReadRepository;
import jhkim593.springboard.common.core.articleread.application.required.repository.BoardArticleCountRepository;
import jhkim593.springboard.common.core.articleread.domain.ArticleRead;
import jhkim593.springboard.common.core.common.event.payload.ArticleDeletedEventPayload;
import jhkim593.springboard.common.core.common.event.payload.ArticleRegisteredEventPayload;
import jhkim593.springboard.common.core.common.event.payload.ArticleUpdatedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleReadUpdateService implements ArticleReadUpdater {
    private final ArticleReadRepository articleReadRepository;
    private final ArticleIdRepository articleIdRepository;
    private final BoardArticleCountRepository boardArticleCountRepository;

    @Override
    public void create(ArticleRegisteredEventPayload payload){
        articleReadRepository.create(ArticleRead.create(payload));
        articleIdRepository.add(payload.getBoardId(), payload.getArticleId());
        boardArticleCountRepository.update(payload.getBoardId(), payload.getBoardArticleCount());
    }
    @Override
    public void delete(ArticleDeletedEventPayload payload){
        articleIdRepository.delete(payload.getBoardId(), payload.getArticleId());
        articleReadRepository.delete(payload.getArticleId());
        boardArticleCountRepository.update(payload.getBoardId(), payload.getBoardArticleCount());
    }
    @Override
    public void update(ArticleUpdatedEventPayload payload){
        articleReadRepository.update(ArticleRead.create(payload));
    }
}
