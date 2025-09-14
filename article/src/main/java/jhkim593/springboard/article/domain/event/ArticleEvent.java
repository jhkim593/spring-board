package jhkim593.springboard.article.domain.event;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jhkim593.springboard.common.event.EventType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ArticleEvent {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private Long articleId;
    private String payload;
    private boolean published;
    private LocalDateTime createdAt;

    public static ArticleEvent create(Long id, EventType eventType, Long articleId, String payload) {
        return ArticleEvent.builder()
                .id(id)
                .eventType(eventType)
                .articleId(articleId)
                .payload(payload)
                .published(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void publishedUpdate() {
        this.published = true;
    }
}
