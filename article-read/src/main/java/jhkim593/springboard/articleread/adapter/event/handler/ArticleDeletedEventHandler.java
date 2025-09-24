package jhkim593.springboard.articleread.adapter.event.handler;

import jhkim593.springboard.articleread.adapter.event.EventHandler;
import jhkim593.springboard.articleread.application.provided.ArticleReadUpdater;
import jhkim593.springboard.common.core.event.EventData;
import jhkim593.springboard.common.core.event.EventType;
import jhkim593.springboard.common.core.event.payload.ArticleDeletedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleDeletedEventHandler implements EventHandler<ArticleDeletedEventPayload> {
    private final ArticleReadUpdater articleReadUpdater;

    @Override
    public void handle(EventData<ArticleDeletedEventPayload> eventData) {
        articleReadUpdater.delete(eventData.getPayload());
    }

    @Override
    public EventType getType() {
        return EventType.ARTICLE_DELETED;
    }
}
