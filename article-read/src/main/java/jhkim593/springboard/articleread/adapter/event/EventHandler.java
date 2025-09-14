package jhkim593.springboard.articleread.adapter.event;

import jhkim593.springboard.common.event.Event;
import jhkim593.springboard.common.event.EventType;
import jhkim593.springboard.common.event.payload.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);
    EventType getType();
}
