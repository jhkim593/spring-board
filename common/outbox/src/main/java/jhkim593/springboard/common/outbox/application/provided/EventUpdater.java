package jhkim593.springboard.common.outbox.application.provided;

import jhkim593.springboard.common.core.event.EventType;
import jhkim593.springboard.common.core.event.payload.EventPayload;
import jhkim593.springboard.common.outbox.domain.Event;

import java.util.List;

public interface EventUpdater {
    Event save(Long id, Long articleId, EventType eventType, EventPayload payload);
    List<Event> findPendingEvents();
    void publishedUpdate(Long id);
}
