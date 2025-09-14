package jhkim593.springboard.article.domain.event;

import jhkim593.springboard.common.event.EventType;
import jhkim593.springboard.common.event.payload.ArticleDeletedEventPayload;
import jhkim593.springboard.common.event.payload.ArticleRegisteredEventPayload;
import lombok.Getter;

@Getter
public class ArticleDeletedEvent {
    private final Long id;
    private final ArticleDeletedEventPayload payload;
    private final EventType eventType;

    public ArticleDeletedEvent(Long id, ArticleDeletedEventPayload payload) {
        this.id = id;
        this.payload = payload;
        this.eventType = EventType.ARTICLE_DELETED;
    }
}
