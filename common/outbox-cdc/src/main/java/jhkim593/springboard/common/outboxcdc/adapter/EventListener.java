package jhkim593.springboard.common.outboxcdc.adapter;

import jhkim593.springboard.common.core.event.EventData;
import jhkim593.springboard.common.outboxcdc.application.provided.EventUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventListener {
    private final EventUpdater eventUpdater;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforeCommitEvent(EventData eventData) {
        eventUpdater.save(
                eventData.getId(),
                eventData.getAggregateId(),
                eventData.getType(),
                eventData.getPayload()
        );
    }
}
