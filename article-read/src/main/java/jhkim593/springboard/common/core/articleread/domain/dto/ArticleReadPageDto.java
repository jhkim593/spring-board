package jhkim593.springboard.common.core.articleread.domain.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ArticleReadPageDto {
    private List<ArticleReadDetailDto> articles;
    private Long articleCount;

    public static ArticleReadPageDto create(List<ArticleReadDetailDto> articles, Long articleCount) {
        ArticleReadPageDto response = new ArticleReadPageDto();
        response.articles = articles;
        response.articleCount = articleCount;
        return response;
    }
}
