package jhkim593.springboard.article.application.provided;


import jhkim593.springboard.common.core.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.core.dto.article.ArticlePageDto;

public interface ArticleFinder {
    ArticlePageDto findArticlePage(Long boardId, Long pageNo, Long pageSize);
    ArticleDetailDto findById(Long id);
}
