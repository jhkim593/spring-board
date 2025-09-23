package jhkim593.springboard.common.core.article.adapter.persistence.jpa;

import jhkim593.springboard.common.core.article.domain.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJpaRepository extends JpaRepository<Article, Long> {
}
