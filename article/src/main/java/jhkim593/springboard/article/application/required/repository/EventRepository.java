package jhkim593.springboard.article.application.required.repository;

import jhkim593.springboard.article.domain.event.ArticleEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends Repository<ArticleEvent, Long> {
    ArticleEvent save(ArticleEvent event);
    Optional<ArticleEvent> findById(Long id);
    List<ArticleEvent> findAllByCreatedAtLessThanEqualOrderByCreatedAtAsc(LocalDateTime from, Pageable pageable);
    Optional<ArticleEvent> findTopByOrderByCreatedAtDesc();
}
