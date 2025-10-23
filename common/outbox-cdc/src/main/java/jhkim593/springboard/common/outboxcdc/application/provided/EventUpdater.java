package jhkim593.springboard.common.outboxcdc.application.provided;

import jhkim593.springboard.common.core.event.EventData;
import jhkim593.springboard.common.outboxcdc.domain.OutboxEvent;


public interface EventUpdater {
    OutboxEvent save(EventData eventData);
}
