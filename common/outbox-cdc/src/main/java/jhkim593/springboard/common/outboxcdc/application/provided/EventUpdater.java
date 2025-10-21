package jhkim593.springboard.common.outboxcdc.application.provided;

import jhkim593.springboard.common.core.event.EventType;
import jhkim593.springboard.common.core.event.payload.EventPayload;
import jhkim593.springboard.common.outboxcdc.domain.OutboxEvent;


public interface EventUpdater {
    OutboxEvent save(Long id, Long articleId, EventType eventType, EventPayload payload);
}
