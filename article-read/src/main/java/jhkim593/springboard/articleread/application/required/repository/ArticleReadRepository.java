package jhkim593.springboard.articleread.application.required.repository;

import jhkim593.springboard.articleread.domain.ArticleRead;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ArticleReadRepository {
    ArticleRead create(ArticleRead articleRead);

    ArticleRead update(ArticleRead articleRead);

    void delete(Long articleId);

    ArticleRead read(Long articleId);

    List<ArticleRead> readAll(List<Long> articleIds);
}
