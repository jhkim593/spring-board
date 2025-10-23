package jhkim593.springboard.common.outboxcdc.domain;

import jakarta.persistence.*;
import jhkim593.springboard.common.core.event.EventData;
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
    private EventType type;
    private Long aggregateId;
    @Column(columnDefinition = "TEXT")
    private String payload;
    private LocalDateTime createdAt;

    public static OutboxEvent create(EventData eventData) {
        return OutboxEvent.builder()
                .id(eventData.getId())
                .type(eventData.getType())
                .aggregateId(eventData.getAggregateId())
                .payload(eventData.payloadJson())
                .createdAt(eventData.getCreatedAt())
                .build();
    }
}
