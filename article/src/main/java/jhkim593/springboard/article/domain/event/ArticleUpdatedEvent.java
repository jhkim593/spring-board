package jhkim593.springboard.article.domain.event;

import jhkim593.springboard.common.event.EventType;
import jhkim593.springboard.common.event.payload.ArticleRegisteredEventPayload;
import jhkim593.springboard.common.event.payload.ArticleUpdatedEventPayload;
import lombok.Getter;

@Getter
public class ArticleUpdatedEvent {
    private final Long id;
    private final ArticleUpdatedEventPayload payload;
    private final EventType eventType;

    public ArticleUpdatedEvent(Long id, ArticleUpdatedEventPayload payload) {
        this.id = id;
        this.payload = payload;
        this.eventType = EventType.ARTICLE_UPDATED;
    }
}
