package jhkim593.springboard.common.outboxcdc.application;

import jhkim593.springboard.common.core.event.EventData;
import jhkim593.springboard.common.outboxcdc.application.provided.EventUpdater;
import jhkim593.springboard.common.outboxcdc.application.required.EventRepository;
import jhkim593.springboard.common.outboxcdc.domain.OutboxEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventUpdateService implements EventUpdater {
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public OutboxEvent save(EventData eventData) {
        return eventRepository.save(OutboxEvent.create(eventData));
    }
}
