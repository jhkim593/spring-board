package jhkim593.springboard.article.application.provided;

import jhkim593.springboard.article.domain.model.Article;
import jhkim593.springboard.article.domain.dto.ArticleRegisterDto;
import jhkim593.springboard.article.domain.dto.ArticleUpdateDto;

public interface ArticleUpdater {
    Article register(ArticleRegisterDto request);
    Article update(Long id, ArticleUpdateDto request);
    void delete(Long id);
}
