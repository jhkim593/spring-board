package jhkim593.springboard.article.application.required.event;

import jhkim593.springboard.article.domain.event.ArticleCreatedEvent;

public interface EventPublisher {
    void publish(ArticleCreatedEvent event);
}
