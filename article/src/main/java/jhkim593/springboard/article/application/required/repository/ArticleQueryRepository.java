package jhkim593.springboard.article.application.required.repository;

import jhkim593.springboard.article.domain.dto.ArticleSummaryDto;

import java.util.List;

public interface ArticleQueryRepository {
    List<ArticleSummaryDto> findArticlePage(Long boardId, Long pageNo, Long pageSize);
    Long countArticlePage(Long boardId, Long limit);
}
