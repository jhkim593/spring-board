package jhkim593.springboard.article.application.provided;

import jhkim593.springboard.common.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.dto.article.ArticlePageDto;

public interface ArticleFinder {
    ArticlePageDto findArticlePage(Long boardId, Long pageNo, Long pageSize);
    ArticleDetailDto findById(Long id);
}
