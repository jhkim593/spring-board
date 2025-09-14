package jhkim593.springboard.article.application;

import jhkim593.springboard.article.application.provided.EventUpdater;
import jhkim593.springboard.article.application.required.repository.EventRepository;
import jhkim593.springboard.article.domain.event.ArticleEvent;
import jhkim593.springboard.common.event.Event;
import jhkim593.springboard.common.event.payload.EventPayload;
import jhkim593.springboard.common.event.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleEventUpdateService implements EventUpdater {
    private final EventRepository eventRepository;
    @Override
    @Transactional
    public ArticleEvent save(Long id, Long articleId, EventType eventType, EventPayload payload) {
        return eventRepository.save(
                ArticleEvent.create(
                        id,
                        eventType,
                        articleId,
                        Event.create(id, eventType, payload).toJson()
                )
        );
    }

    @Override
    public List<ArticleEvent> findPendingEvents() {
        return eventRepository.findAllByCreatedAtLessThanEqualOrderByCreatedAtAsc(
                LocalDateTime.now().minusSeconds(10),
                Pageable.ofSize(100)
        );
    }

    @Override
    @Transactional
    public void publishedUpdate(Long id) {
        ArticleEvent event = eventRepository.findById(id).orElseThrow();
        event.publishedUpdate();
    }
}
