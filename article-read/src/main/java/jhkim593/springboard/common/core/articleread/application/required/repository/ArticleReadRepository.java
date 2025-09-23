package jhkim593.springboard.common.core.articleread.application.required.repository;

import jhkim593.springboard.common.core.articleread.domain.ArticleRead;

import java.util.List;

public interface ArticleReadRepository {
    ArticleRead create(ArticleRead articleRead);

    ArticleRead update(ArticleRead articleRead);

    void delete(Long articleId);

    ArticleRead read(Long articleId);

    List<ArticleRead> readAll(List<Long> articleIds);
}
