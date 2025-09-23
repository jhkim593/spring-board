package jhkim593.springboard.article.domain.event;

import jhkim593.springboard.common.event.model.EventData;
import jhkim593.springboard.common.event.model.EventType;
import jhkim593.springboard.common.event.payload.ArticleDeletedEventPayload;
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
