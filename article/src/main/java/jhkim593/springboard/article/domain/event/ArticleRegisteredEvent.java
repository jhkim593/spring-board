package jhkim593.springboard.article.domain.event;

import jhkim593.springboard.common.core.event.EventData;
import jhkim593.springboard.common.core.event.EventType;
import jhkim593.springboard.common.core.event.payload.ArticleRegisteredEventPayload;
import lombok.Getter;

@Getter
public class ArticleRegisteredEvent extends EventData<ArticleRegisteredEventPayload> {

    public ArticleRegisteredEvent(Long eventId, ArticleRegisteredEventPayload payload) {
        super(
                eventId,
                payload.getArticleId(),
                EventType.ARTICLE_REGISTERED,
                payload
        );
    }
}
