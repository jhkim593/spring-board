package jhkim593.springboard.article.application.provided;

import jhkim593.springboard.article.domain.event.ArticleCreatedEvent;
import jhkim593.springboard.article.domain.event.Event;

public interface EventUpdater {
    Event save(ArticleCreatedEvent event);
}
