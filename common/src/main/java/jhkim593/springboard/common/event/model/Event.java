package jhkim593.springboard.common.event.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Event {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private Long articleId;
    @Column(columnDefinition = "TEXT")
    private String message;
    private boolean published;
    private LocalDateTime createdAt;

    public static Event create(Long id, EventType eventType, Long articleId, String message) {
        return Event.builder()
                .id(id)
                .eventType(eventType)
                .articleId(articleId)
                .message(message)
                .published(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void publishedUpdate() {
        this.published = true;
    }
}
