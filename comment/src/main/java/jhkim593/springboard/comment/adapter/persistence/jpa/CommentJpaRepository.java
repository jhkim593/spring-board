package jhkim593.springboard.comment.adapter.persistence.jpa;

import jhkim593.springboard.comment.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.parent WHERE c.commentId = :commentId")
    Optional<Comment> findById (@Param("commentId") Long commentId);
}
