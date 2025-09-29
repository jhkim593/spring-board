package jhkim593.springboard.common.outbox.application.required;

import jhkim593.springboard.common.outbox.domain.OutboxEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findAllByCreatedAtLessThanEqualAndPublishedFalseOrderByCreatedAtAsc(LocalDateTime from, Pageable pageable);
    Optional<OutboxEvent> findTopByOrderByCreatedAtDesc();
}
