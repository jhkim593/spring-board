package jhkim593.springboard.articleread.adapter.event;

import jhkim593.springboard.common.event.model.EventData;
import jhkim593.springboard.common.event.model.EventType;
import jhkim593.springboard.common.event.payload.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(EventData<T> eventData);
    EventType getType();
}
