package jhkim593.springboard.common.core.article.adapter.persistence.jpa;

import jhkim593.springboard.common.core.article.domain.model.BoardArticleCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardArticleCountJpaRepository extends JpaRepository<BoardArticleCount, Long> {
}
