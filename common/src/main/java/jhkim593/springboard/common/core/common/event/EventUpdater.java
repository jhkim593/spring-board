package jhkim593.springboard.common.core.common.event;

import jhkim593.springboard.common.core.common.event.model.Event;
import jhkim593.springboard.common.core.common.event.model.EventType;
import jhkim593.springboard.common.core.common.event.payload.EventPayload;

import java.util.List;

public interface EventUpdater {
    Event save(Long id, Long articleId, EventType eventType, EventPayload payload);
    List<Event> findPendingEvents();
    void publishedUpdate(Long id);
}
