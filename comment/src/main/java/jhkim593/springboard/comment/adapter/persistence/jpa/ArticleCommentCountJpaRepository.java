package jhkim593.springboard.comment.adapter.persistence.jpa;

import jhkim593.springboard.comment.domain.model.ArticleCommentCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentCountJpaRepository extends JpaRepository<ArticleCommentCount,Long> {
}
