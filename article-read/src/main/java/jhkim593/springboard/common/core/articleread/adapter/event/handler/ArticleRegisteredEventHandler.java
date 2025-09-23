package jhkim593.springboard.common.core.articleread.adapter.event.handler;

import jhkim593.springboard.common.core.articleread.adapter.event.EventHandler;
import jhkim593.springboard.common.core.articleread.application.provided.ArticleReadUpdater;
import jhkim593.springboard.common.core.common.event.model.EventData;
import jhkim593.springboard.common.core.common.event.model.EventType;
import jhkim593.springboard.common.core.common.event.payload.ArticleRegisteredEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleRegisteredEventHandler implements EventHandler<ArticleRegisteredEventPayload> {
    private final ArticleReadUpdater articleUpdater;

    @Override
    public void handle(EventData<ArticleRegisteredEventPayload> eventData) {
        articleUpdater.create(eventData.getPayload());
    }

    @Override
    public EventType getType() {
        return EventType.ARTICLE_REGISTERED;
    }
}
