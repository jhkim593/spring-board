package jhkim593.springboard.common.core.article.application.provided;

import jhkim593.springboard.common.core.article.domain.model.Article;
import jhkim593.springboard.common.core.article.domain.dto.ArticleRegisterDto;
import jhkim593.springboard.common.core.article.domain.dto.ArticleUpdateDto;

public interface ArticleUpdater {
    Article register(ArticleRegisterDto request);
    Article update(Long id, ArticleUpdateDto request);
    Article delete(Long id);
}
