package jhkim593.springboard.articleread.adapter.event;

import jhkim593.springboard.articleread.application.provided.ArticleReadFinder;
import jhkim593.springboard.common.event.Event;
import jhkim593.springboard.common.event.EventType;
import jhkim593.springboard.common.event.Topic;
import jhkim593.springboard.common.event.payload.EventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventListener {
    private final ArticleReadFinder finder;

    @KafkaListener(topics = {
            Topic.ARTICLE
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("[ArticleReadEventConsumer.listen] message={}", message);
        Event<EventPayload> event = Event.fromJson(message);
        if (event.getType().equals(EventType.ARTICLE_REGISTERED)) {

        }
        if (event != null) {
//            articleReadService.handleEvent(event);
        }
        ack.acknowledge();
    }
}
