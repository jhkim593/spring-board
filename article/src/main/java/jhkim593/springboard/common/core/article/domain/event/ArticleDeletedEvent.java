package jhkim593.springboard.common.core.article.domain.event;

import jhkim593.springboard.common.core.common.event.model.EventData;
import jhkim593.springboard.common.core.common.event.model.EventType;
import jhkim593.springboard.common.core.common.event.payload.ArticleDeletedEventPayload;
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
