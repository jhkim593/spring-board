package jhkim593.springboard.common.core.event.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleViewedEventPayload implements EventPayload {
    private Long articleId;
    private Long articleViewCount;
}
