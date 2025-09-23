package jhkim593.springboard.common.core.common.event.model;

import jhkim593.springboard.common.core.common.event.payload.*;
import jhkim593.springboard.common.event.payload.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    ARTICLE_REGISTERED(ArticleRegisteredEventPayload.class, Topic.ARTICLE),
    ARTICLE_UPDATED(ArticleUpdatedEventPayload.class, Topic.ARTICLE),
    ARTICLE_DELETED(ArticleDeletedEventPayload.class, Topic.ARTICLE),
    COMMENT_CREATED(CommentCreatedEventPayload.class, Topic.COMMENT),
    COMMENT_DELETED(CommentDeletedEventPayload.class, Topic.COMMENT),
    ARTICLE_LIKED(ArticleLikedEventPayload.class, Topic.LIKE),
    ARTICLE_UNLIKED(ArticleUnlikedEventPayload.class, Topic.LIKE),
    ARTICLE_VIEWED(ArticleViewedEventPayload.class, Topic.VIEW);

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        try {
            return valueOf(type);
        } catch (Exception e) {
            log.error("[EventType.from] type={}", type, e);
            return null;
        }
    }
}
