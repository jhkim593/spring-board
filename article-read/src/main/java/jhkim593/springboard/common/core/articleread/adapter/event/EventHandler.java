package jhkim593.springboard.common.core.articleread.adapter.event;

import jhkim593.springboard.common.core.common.event.model.EventData;
import jhkim593.springboard.common.core.common.event.model.EventType;
import jhkim593.springboard.common.core.common.event.payload.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(EventData<T> eventData);
    EventType getType();
}
