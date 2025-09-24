package jhkim593.springboard.articleread.adapter.event.handler;
import jhkim593.springboard.articleread.adapter.event.EventHandler;
import jhkim593.springboard.articleread.application.provided.ArticleReadUpdater;
import jhkim593.springboard.common.core.event.EventData;
import jhkim593.springboard.common.core.event.EventType;
import jhkim593.springboard.common.core.event.payload.ArticleUpdatedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleUpdatedEventHandler implements EventHandler<ArticleUpdatedEventPayload> {
    private final ArticleReadUpdater articleReadUpdater;

    @Override
    public void handle(EventData<ArticleUpdatedEventPayload> eventData) {
        articleReadUpdater.update(eventData.getPayload());
    }

    @Override
    public EventType getType() {
        return EventType.ARTICLE_UPDATED;
    }
}

