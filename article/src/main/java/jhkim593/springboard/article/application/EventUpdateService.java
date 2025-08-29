package jhkim593.springboard.article.application;

import jhkim593.springboard.article.application.provided.EventUpdater;
import jhkim593.springboard.article.application.required.repository.EventRepository;
import jhkim593.springboard.article.domain.event.ArticleCreatedEvent;
import jhkim593.springboard.article.domain.event.Event;
import jhkim593.springboard.common.event.EventType;
import jhkim593.springboard.common.snowflake.DBIdGenerator;
import jhkim593.springboard.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventUpdateService implements EventUpdater {
    private final EventRepository eventRepository;
    private final DBIdGenerator dbIdGenerator;
    @Override
    public Event save(ArticleCreatedEvent event) {
        eventRepository.save(
                Event.builder()
                        .id(dbIdGenerator.getId())
                        .eventType(EventType.ARTICLE_CREATED)
                        .createdAt(LocalDateTime.now())
                        .payload()

        )
    }
}
