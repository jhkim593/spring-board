package jhkim593.springboard.common.core.article.application.required.event;

import jhkim593.springboard.common.core.common.event.payload.ArticleDeletedEventPayload;
import jhkim593.springboard.common.core.common.event.payload.ArticleRegisteredEventPayload;
import jhkim593.springboard.common.core.common.event.payload.ArticleUpdatedEventPayload;

public interface EventPublisher {
    void registeredEventPublish(ArticleRegisteredEventPayload payload);
    void deletedEventPublish(ArticleDeletedEventPayload payload);
    void updatedEventPublish(ArticleUpdatedEventPayload payload);
}
