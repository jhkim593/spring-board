package jhkim593.springboard.article.domain.event;

import jhkim593.springboard.common.core.event.EventData;
import jhkim593.springboard.common.core.event.EventType;
import jhkim593.springboard.common.core.event.payload.ArticleDeletedEventPayload;
import lombok.Getter;

@Getter
public class ArticleDeletedEvent extends EventData<ArticleDeletedEventPayload> {

    public ArticleDeletedEvent(Long eventId, ArticleDeletedEventPayload payload) {
        super(
                eventId,
                payload.getArticleId(),
                EventType.ARTICLE_DELETED,
                payload
        );
    }
}
