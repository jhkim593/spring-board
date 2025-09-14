package jhkim593.springboard.article.application.provided;

import jhkim593.springboard.article.domain.event.ArticleEvent;
import jhkim593.springboard.common.event.payload.EventPayload;
import jhkim593.springboard.common.event.EventType;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface EventUpdater {
    ArticleEvent save(Long id, Long articleId, EventType eventType, EventPayload payload);
    List<ArticleEvent> findPendingEvents();
    void publishedUpdate(Long id);
}
