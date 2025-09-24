package jhkim593.springboard.articleread.adapter.event;

import jhkim593.springboard.common.core.event.EventData;
import jhkim593.springboard.common.core.event.Topic;
import jhkim593.springboard.common.core.event.payload.EventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventListener {
    private final EventHandlerFactory eventHandlerFactory;

    @KafkaListener(topics = {
            Topic.ARTICLE
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("[ArticleReadEventConsumer.listen] message={}", message);
        EventData<EventPayload> eventData = EventData.fromJson(message);
        EventHandler eventHandler = eventHandlerFactory.get(eventData.getType());
        eventHandler.handle(eventData);
        ack.acknowledge();
    }
}
