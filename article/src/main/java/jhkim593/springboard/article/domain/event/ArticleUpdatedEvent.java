package jhkim593.springboard.article.domain.event;

import jhkim593.springboard.common.event.model.EventData;
import jhkim593.springboard.common.event.model.EventType;
import jhkim593.springboard.common.event.payload.ArticleUpdatedEventPayload;
import lombok.Getter;

@Getter
public class ArticleUpdatedEvent extends EventData<ArticleUpdatedEventPayload> {

    public ArticleUpdatedEvent(Long eventId, ArticleUpdatedEventPayload payload) {
        super(
                eventId,
                payload.getArticleId(),
                EventType.ARTICLE_UPDATED,
                payload
        );
    }
}
