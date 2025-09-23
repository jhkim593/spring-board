package jhkim593.springboard.common.core.articleread.application.provided;

import jhkim593.springboard.common.core.common.event.payload.ArticleDeletedEventPayload;
import jhkim593.springboard.common.core.common.event.payload.ArticleRegisteredEventPayload;
import jhkim593.springboard.common.core.common.event.payload.ArticleUpdatedEventPayload;

public interface ArticleReadUpdater {
    void create(ArticleRegisteredEventPayload payload);

    void delete(ArticleDeletedEventPayload payload);

    void update(ArticleUpdatedEventPayload payload);
}
