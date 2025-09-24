package jhkim593.springboard.article.adapter.persistence.jpa;

import jhkim593.springboard.article.domain.model.BoardArticleCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardArticleCountJpaRepository extends JpaRepository<BoardArticleCount, Long> {
}
