package jhkim593.springboard.article.domain.event;

import jhkim593.springboard.common.event.EventType;
import jhkim593.springboard.common.event.payload.ArticleCreatedEventPayload;

public record ArticleCreatedEvent (
        ArticleCreatedEventPayload payload
){
    public EventType eventType() {
        return EventType.ARTICLE_CREATED;
    }
}