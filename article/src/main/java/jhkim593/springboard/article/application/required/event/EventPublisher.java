package jhkim593.springboard.article.application.required.event;

import jhkim593.springboard.common.event.payload.ArticleDeletedEventPayload;
import jhkim593.springboard.common.event.payload.ArticleRegisteredEventPayload;
import jhkim593.springboard.common.event.payload.ArticleUpdatedEventPayload;

public interface EventPublisher {
    void registeredEventPublish(ArticleRegisteredEventPayload payload);
    void deletedEventPublish(ArticleDeletedEventPayload payload);
    void updatedEventPublish(ArticleUpdatedEventPayload payload);
}
