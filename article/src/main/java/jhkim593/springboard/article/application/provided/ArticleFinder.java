package jhkim593.springboard.article.application.provided;

import jhkim593.springboard.article.domain.dto.ArticlePageDto;
import jhkim593.springboard.article.domain.Article;

public interface ArticleFinder {
    ArticlePageDto findArticlePage(Long boardId, Long pageNo, Long pageSize);
    Article findById(Long id);
}
