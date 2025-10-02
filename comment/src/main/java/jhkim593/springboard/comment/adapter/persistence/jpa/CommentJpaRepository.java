package jhkim593.springboard.comment.adapter.persistence.jpa;

import jhkim593.springboard.comment.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment,Long> {
}
