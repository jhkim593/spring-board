package jhkim593.springboard.common.outbox.application;

import jhkim593.springboard.common.core.event.EventData;
import jhkim593.springboard.common.core.event.EventType;
import jhkim593.springboard.common.core.event.payload.EventPayload;
import jhkim593.springboard.common.outbox.application.provided.EventUpdater;
import jhkim593.springboard.common.outbox.domain.Event;
import jhkim593.springboard.common.outbox.application.required.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventUpdateService implements EventUpdater {
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Event save(Long id, Long aggregateId, EventType eventType, EventPayload payload) {
        return eventRepository.save(
                Event.create(
                        id,
                        eventType,
                        aggregateId,
                        EventData.create(id, aggregateId, eventType, payload).toJson()
                )
        );
    }

    @Override
    public List<Event> findPendingEvents() {
        return eventRepository.findAllByCreatedAtLessThanEqualAndPublishedFalseOrderByCreatedAtAsc(
                LocalDateTime.now().minusSeconds(10),
                Pageable.ofSize(100)
        );
    }

    @Override
    @Transactional
    public void publishedUpdate(Long id) {
        Event event = eventRepository.findById(id).orElseThrow();
        event.publishedUpdate();
    }
}
