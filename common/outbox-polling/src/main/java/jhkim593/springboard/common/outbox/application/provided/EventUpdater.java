package jhkim593.springboard.common.outbox.application.provided;

import jhkim593.springboard.common.core.event.EventType;
import jhkim593.springboard.common.core.event.payload.EventPayload;
import jhkim593.springboard.common.outbox.domain.OutboxEvent;

import java.util.List;

public interface EventUpdater {
    OutboxEvent save(Long id, Long articleId, EventType eventType, EventPayload payload);
    List<OutboxEvent> findPendingEvents();
    void publishedUpdate(Long id);
}
