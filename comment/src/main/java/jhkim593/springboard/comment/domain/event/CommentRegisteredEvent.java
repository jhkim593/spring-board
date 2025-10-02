package jhkim593.springboard.comment.domain.event;

import jhkim593.springboard.common.core.event.EventData;
import jhkim593.springboard.common.core.event.EventType;
import jhkim593.springboard.common.core.event.payload.CommentRegisteredEventPayload;
import lombok.Getter;

@Getter
public class CommentRegisteredEvent extends EventData<CommentRegisteredEventPayload> {

    public CommentRegisteredEvent(Long eventId, CommentRegisteredEventPayload payload) {
        super(
                eventId,
                payload.getArticleId(),
                EventType.COMMENT_REGISTERED,
                payload
        );
    }
}
