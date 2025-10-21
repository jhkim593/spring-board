package jhkim593.springboard.common.outboxcdc.domain;

import jakarta.persistence.*;
import jhkim593.springboard.common.core.event.EventType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OutboxEvent {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private Long aggregateId;
    @Column(columnDefinition = "TEXT")
    private String message;
    private boolean published;
    private LocalDateTime createdAt;

    public static OutboxEvent create(Long id, EventType eventType, Long aggregateId, String message) {
        return OutboxEvent.builder()
                .id(id)
                .eventType(eventType)
                .aggregateId(aggregateId)
                .message(message)
                .published(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void publishedUpdate() {
        this.published = true;
    }
}
