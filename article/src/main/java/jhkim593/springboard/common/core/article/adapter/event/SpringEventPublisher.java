package jhkim593.springboard.common.core.article.adapter.event;

import jhkim593.springboard.common.core.article.application.required.event.EventPublisher;
import jhkim593.springboard.common.core.article.domain.event.ArticleDeletedEvent;
import jhkim593.springboard.common.core.article.domain.event.ArticleRegisteredEvent;
import jhkim593.springboard.common.core.article.domain.event.ArticleUpdatedEvent;
import jhkim593.springboard.common.core.common.event.payload.ArticleDeletedEventPayload;
import jhkim593.springboard.common.core.common.event.payload.ArticleRegisteredEventPayload;
import jhkim593.springboard.common.core.common.event.payload.ArticleUpdatedEventPayload;
import jhkim593.springboard.common.core.common.snowflake.DBIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEventPublisher implements EventPublisher {
    private final ApplicationEventPublisher eventPublisher;
    private final DBIdGenerator idGenerator;

    @Override
    public void registeredEventPublish(ArticleRegisteredEventPayload payload) {
        eventPublisher.publishEvent(
                new ArticleRegisteredEvent(
                        idGenerator.getId(),
                        payload)
        );
    }

    @Override
    public void deletedEventPublish(ArticleDeletedEventPayload payload) {
        eventPublisher.publishEvent(
                new ArticleDeletedEvent(
                        idGenerator.getId(),
                        payload)
        );
    }

    @Override
    public void updatedEventPublish(ArticleUpdatedEventPayload payload) {
        eventPublisher.publishEvent(
                new ArticleUpdatedEvent(
                        idGenerator.getId(),
                        payload)
        );
    }
}
