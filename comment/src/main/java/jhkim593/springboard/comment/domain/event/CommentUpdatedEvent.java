package jhkim593.springboard.comment.domain.event;

import jhkim593.springboard.common.core.event.EventData;
import jhkim593.springboard.common.core.event.EventType;
import jhkim593.springboard.common.core.event.payload.CommentDeletedEventPayload;
import jhkim593.springboard.common.core.event.payload.CommentUpdatedEventPayload;
import lombok.Getter;

@Getter
public class CommentUpdatedEvent extends EventData<CommentUpdatedEventPayload> {

    public CommentUpdatedEvent(Long eventId, CommentUpdatedEventPayload payload) {
        super(
                eventId,
                payload.getArticleId(),
                EventType.COMMENT_UPDATED,
                payload
        );
    }
}
