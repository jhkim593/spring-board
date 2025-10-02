package jhkim593.springboard.common.outbox.adapter;

import jhkim593.springboard.common.core.event.EventType;
import jhkim593.springboard.common.outbox.application.provided.EventUpdater;
import jhkim593.springboard.common.outbox.domain.OutboxEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventPublisher {
    private final EventUpdater eventUpdater;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(
            fixedDelay = 10,
            initialDelay = 5,
            timeUnit = TimeUnit.SECONDS,
            scheduler = "messageRelayPublishPendingEventExecutor"
    )
    public void publishPendingEvent() {
        List<OutboxEvent> outboxEvents = eventUpdater.findPendingEvents();
        for (OutboxEvent outboxEvent : outboxEvents) {
            try {
                publishEvent(
                        outboxEvent.getId(),
                        outboxEvent.getEventType(),
                        outboxEvent.getAggregateId().toString(),
                        outboxEvent.getMessage()
                );
            } catch (Exception e) {
                log.error("pending event publish fail", e);
            }
        }
    }
    public void publishEvent(Long eventId, EventType type, String partitionKey, String payload) throws Exception {
        try {
            kafkaTemplate.send(
                    type.getTopic(),
                    partitionKey,
                    payload
            ).get(1, TimeUnit.SECONDS);
            eventUpdater.publishedUpdate(eventId);
        } catch (Exception e) {
            log.error("event send fail", e);
            throw e;
        }
    }
}
