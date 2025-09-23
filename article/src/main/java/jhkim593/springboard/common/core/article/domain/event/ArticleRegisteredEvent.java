package jhkim593.springboard.common.core.article.domain.event;

import jhkim593.springboard.common.core.common.event.model.EventData;
import jhkim593.springboard.common.core.common.event.model.EventType;
import jhkim593.springboard.common.core.common.event.payload.ArticleRegisteredEventPayload;
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
