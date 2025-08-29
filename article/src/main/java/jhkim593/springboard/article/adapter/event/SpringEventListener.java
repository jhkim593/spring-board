package jhkim593.springboard.article.adapter.event;

import jhkim593.springboard.article.application.provided.EventUpdater;
import jhkim593.springboard.article.application.required.repository.EventRepository;
import jhkim593.springboard.article.domain.event.ArticleCreatedEvent;
import jhkim593.springboard.article.domain.event.Event;
import jhkim593.springboard.common.event.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SpringEventListener {
    private final EventUpdater eventUpdater;
    private final MessageRelayCoordinator messageRelayCoordinator;
    private final KafkaTemplate<String, String> messageRelayKafkaTemplate;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void createOutbox(ArticleCreatedEvent event) {
        eventRepository.save(
                Event.builder()
                        .eventType(EventType.ARTICLE_CREATED)
                        .id()
        )
    }

}
