package jhkim593.springboard.articleread.adapter.event.handler;

import jhkim593.springboard.articleread.adapter.event.EventHandler;
import jhkim593.springboard.articleread.application.required.repository.ArticleIdRepository;
import jhkim593.springboard.articleread.application.required.repository.ArticleReadRepository;
import jhkim593.springboard.articleread.application.required.repository.BoardArticleCountRepository;
import jhkim593.springboard.articleread.domain.ArticleRead;
import jhkim593.springboard.common.event.Event;
import jhkim593.springboard.common.event.EventType;
import jhkim593.springboard.common.event.payload.ArticleRegisteredEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleRegisteredEventHandler implements EventHandler<ArticleRegisteredEventPayload> {
    private final ArticleIdRepository articleIdRepository;
    private final ArticleReadRepository articleReadRepository;
    private final BoardArticleCountRepository boardArticleCountRepository;

    @Override
    public void handle(Event<ArticleRegisteredEventPayload> event) {
        ArticleRegisteredEventPayload payload = event.getPayload();
        articleReadRepository.create(ArticleRead.create(payload));
        articleIdRepository.add(payload.getBoardId(), payload.getArticleId());
        boardArticleCountRepository.update(payload.getBoardId(), payload.getBoardArticleCount());
    }

    @Override
    public EventType getType() {
        return EventType.ARTICLE_REGISTERED;
    }
}
