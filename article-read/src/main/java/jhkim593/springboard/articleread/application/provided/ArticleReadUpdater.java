package jhkim593.springboard.articleread.application.provided;

import jhkim593.springboard.common.event.payload.ArticleDeletedEventPayload;
import jhkim593.springboard.common.event.payload.ArticleRegisteredEventPayload;
import jhkim593.springboard.common.event.payload.ArticleUpdatedEventPayload;

public interface ArticleReadUpdater {
    void create(ArticleRegisteredEventPayload payload);

    void delete(ArticleDeletedEventPayload payload);

    void update(ArticleUpdatedEventPayload payload);
}
