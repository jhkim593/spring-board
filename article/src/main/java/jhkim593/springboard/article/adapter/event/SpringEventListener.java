package jhkim593.springboard.article.adapter.event;

import jhkim593.springboard.article.application.provided.EventUpdater;
import jhkim593.springboard.article.domain.event.ArticleDeletedEvent;
import jhkim593.springboard.article.domain.event.ArticleEvent;
import jhkim593.springboard.article.domain.event.ArticleRegisteredEvent;
import jhkim593.springboard.article.domain.event.ArticleUpdatedEvent;
import jhkim593.springboard.common.event.DataSerializer;
import jhkim593.springboard.common.event.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpringEventListener {
    private final EventUpdater eventUpdater;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void registerEvent(ArticleRegisteredEvent event) {
        eventUpdater.save(
                event.getId(),
                event.getPayload().getArticleId(),
                event.getEventType(),
                event.getPayload()
        );
    }

    @Async("messageRelayPublishEventExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishEvent(ArticleRegisteredEvent event) throws Exception {
        try {
            publishEvent(
                    event.getId(),
                    event.getEventType(),
                    event.getPayload().getArticleId().toString(),
                    DataSerializer.serialize(event));
        } catch (Exception e) {
            log.error("event publish fail", e);
            throw e;
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void registerEvent(ArticleDeletedEvent event) {
        eventUpdater.save(
                event.getId(),
                event.getPayload().getArticleId(),
                event.getEventType(),
                event.getPayload()
        );
    }

    @Async("messageRelayPublishEventExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishEvent(ArticleDeletedEvent event) throws Exception {
        try {
            publishEvent(
                    event.getId(),
                    event.getEventType(),
                    event.getPayload().getArticleId().toString(),
                    DataSerializer.serialize(event));
        } catch (Exception e) {
            log.error("event publish fail", e);
            throw e;
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void registerEvent(ArticleUpdatedEvent event) {
        eventUpdater.save(
                event.getId(),
                event.getPayload().getArticleId(),
                event.getEventType(),
                event.getPayload()
        );
    }

    @Async("messageRelayPublishEventExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishEvent(ArticleUpdatedEvent event) throws Exception {
        try {
            publishEvent(
                    event.getId(),
                    event.getEventType(),
                    event.getPayload().getArticleId().toString(),
                    DataSerializer.serialize(event));
        } catch (Exception e) {
            log.error("event publish fail", e);
            throw e;
        }
    }

    private void publishEvent(Long eventId, EventType type, String partitionKey, String payload) throws Exception {
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

    //TODO: class 분리
    @Scheduled(
            fixedDelay = 10,
            initialDelay = 5,
            timeUnit = TimeUnit.SECONDS,
            scheduler = "messageRelayPublishPendingEventExecutor"
    )
    public void publishPendingEvent() {
//        AssignedShard assignedShard = messageRelayCoordinator.assignShards();
//        log.info("[MessageRelay.publishPendingEvent] assignedShard size={}", assignedShard.getShards().size());
        List<ArticleEvent> events = eventUpdater.findPendingEvents();
        for (ArticleEvent event : events) {
            try {
                publishEvent(
                        event.getId(),
                        event.getEventType(),
                        event.getArticleId().toString(),
                        event.getPayload()
                );
            } catch (Exception e) {
                log.error("pending event publish fail", e);
            }
        }
    }

}
