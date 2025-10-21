package jhkim593.springboard.common.outboxcdc.application.required;

import jhkim593.springboard.common.outboxcdc.domain.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<OutboxEvent, Long> {
}
