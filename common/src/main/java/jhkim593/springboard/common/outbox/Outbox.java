package jhkim593.springboard.common.outbox;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jhkim593.springboard.common.event.EventType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outbox {
    @Id
    private Long outboxId;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String payload;
//    private Long shardKey;
    private LocalDateTime createdAt;

    public static Outbox create(Long outboxId, EventType eventType, String payload) {
        Outbox outbox = new Outbox();
        outbox.outboxId = outboxId;
        outbox.eventType = eventType;
        outbox.payload = payload;
//        outbox.shardKey = shardKey;
        outbox.createdAt = LocalDateTime.now();
        return outbox;
    }
}
