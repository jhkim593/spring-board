package jhkim593.springboard.comment.domain.event;

import jhkim593.springboard.common.core.event.EventData;
import jhkim593.springboard.common.core.event.EventType;
import jhkim593.springboard.common.core.event.payload.CommentDeletedEventPayload;
import lombok.Getter;

@Getter
public class CommentUpdatedEvent extends EventData<CommentDeletedEventPayload> {

    public CommentUpdatedEvent(Long eventId, CommentDeletedEventPayload payload) {
        super(
                eventId,
                payload.getArticleId(),
                EventType.ARTICLE_DELETED,
                payload
        );
    }
}
