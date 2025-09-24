package jhkim593.springboard.common.core.dto.article;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ArticlePageDto {
    private List<ArticleDetailDto> data;
    private Long totalCount;

    public static ArticlePageDto create(List<ArticleDetailDto> articles, Long articleCount) {
        return ArticlePageDto.builder()
                .data(articles)
                .totalCount(articleCount)
                .build();
    }
}
