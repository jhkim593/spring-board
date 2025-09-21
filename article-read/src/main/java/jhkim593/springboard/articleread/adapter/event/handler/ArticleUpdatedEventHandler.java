package jhkim593.springboard.articleread.adapter.event.handler;
import jhkim593.springboard.articleread.adapter.event.EventHandler;
import jhkim593.springboard.articleread.application.provided.ArticleReadFinder;
import jhkim593.springboard.articleread.application.provided.ArticleReadUpdater;
import jhkim593.springboard.common.event.Event;
import jhkim593.springboard.common.event.EventType;
import jhkim593.springboard.common.event.payload.ArticleUpdatedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleUpdatedEventHandler implements EventHandler<ArticleUpdatedEventPayload> {
    private final ArticleReadUpdater articleReadUpdater;

    @Override
    public void handle(Event<ArticleUpdatedEventPayload> event) {
        articleReadUpdater.update(event.getPayload());
    }

    @Override
    public EventType getType() {
        return EventType.ARTICLE_UPDATED;
    }
}

