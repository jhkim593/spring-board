package jhkim593.springboard.articleread.adapter.event.handler;

import jhkim593.springboard.articleread.adapter.event.EventHandler;
import jhkim593.springboard.articleread.application.provided.ArticleReadUpdater;
import jhkim593.springboard.common.event.model.EventData;
import jhkim593.springboard.common.event.model.EventType;
import jhkim593.springboard.common.event.payload.ArticleRegisteredEventPayload;
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
