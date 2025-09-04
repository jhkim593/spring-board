package jhkim593.springboard.article.application;

import jhkim593.springboard.article.application.provided.EventUpdater;
import jhkim593.springboard.article.application.required.repository.EventRepository;
import jhkim593.springboard.article.domain.event.ArticleCreatedEvent;
import jhkim593.springboard.article.domain.event.MemberOutbox;
import jhkim593.springboard.common.event.EventType;
import jhkim593.springboard.common.snowflake.DBIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberOutboxUpdateService implements EventUpdater {
    private final EventRepository eventRepository;
    private final DBIdGenerator dbIdGenerator;
    @Override
    public MemberOutbox save(ArticleCreatedEvent event) {
        eventRepository.save(
                MemberOutbox.builder()
                        .id(dbIdGenerator.getId())
                        .eventType(EventType.ARTICLE_CREATED)
                        .createdAt(LocalDateTime.now())
                        .payload(
                                event.payload())
                        .build()

        )
    }
}
