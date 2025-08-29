package jhkim593.springboard.article.domain.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class ArticlePageDto {
    private List<ArticleSummaryDto> data;
    private Long articleCount;

    public static ArticlePageDto create(List<ArticleSummaryDto> articles, Long articleCount) {
        return ArticlePageDto.builder()
                .data(articles)
                .articleCount(articleCount)
                .build();
    }
}
