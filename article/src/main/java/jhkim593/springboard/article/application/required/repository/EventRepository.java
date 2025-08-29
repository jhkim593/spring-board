package jhkim593.springboard.article.application.required.repository;

import jhkim593.springboard.article.domain.event.Event;
import org.springframework.data.repository.Repository;

public interface EventRepository extends Repository<Event, Long> {
    Event save(Event event);
}
