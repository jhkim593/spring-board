package jhkim593.springboard.article.application.required.repository;

import jhkim593.springboard.common.dto.article.ArticleDetailDto;

import java.util.List;

public interface ArticleQueryRepository {
    List<ArticleDetailDto> findArticlePage(Long boardId, Long pageNo, Long pageSize);
}
