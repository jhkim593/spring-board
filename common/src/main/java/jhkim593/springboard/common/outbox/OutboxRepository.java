package jhkim593.springboard.common.outbox;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;


import java.time.LocalDateTime;
import java.util.List;

public interface OutboxRepository extends Repository<Outbox, Long> {
    List<Outbox> findAllByShardKeyAndCreatedAtLessThanEqualOrderByCreatedAtAsc(
//            Long shardKey,
            LocalDateTime from,
            Pageable pageable
    );
    Outbox save(Outbox outbox);
    void delete(Outbox outbox);
}
