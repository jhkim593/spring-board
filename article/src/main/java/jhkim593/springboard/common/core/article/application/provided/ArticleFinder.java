package jhkim593.springboard.common.core.article.application.provided;

import jhkim593.springboard.common.core.common.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.core.common.dto.article.ArticlePageDto;

public interface ArticleFinder {
    ArticlePageDto findArticlePage(Long boardId, Long pageNo, Long pageSize);
    ArticleDetailDto findById(Long id);
}
