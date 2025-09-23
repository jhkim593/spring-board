package jhkim593.springboard.common.event.repository;

import jhkim593.springboard.common.event.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByCreatedAtLessThanEqualOrderByCreatedAtAsc(LocalDateTime from, Pageable pageable);
    Optional<Event> findTopByOrderByCreatedAtDesc();
}
