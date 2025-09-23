package jhkim593.springboard.common.core.article.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleUpdateDto {
    private String title;
    private String content;
}
