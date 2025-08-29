package jhkim593.springboard.article.adapter.event;

import jhkim593.springboard.article.application.required.event.EventPublisher;
import jhkim593.springboard.article.domain.event.ArticleCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEventPublisher implements EventPublisher {
    private final ApplicationEventPublisher eventPublisher;
    @Override
    public void publish(ArticleCreatedEvent event) {
        eventPublisher.publishEvent(event);
    }
}
