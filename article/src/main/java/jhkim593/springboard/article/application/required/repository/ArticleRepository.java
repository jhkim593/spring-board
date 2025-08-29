package jhkim593.springboard.article.application.required.repository;

import jhkim593.springboard.article.domain.Article;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface ArticleRepository extends Repository<Article, Long> {
    Optional<Article> findById(Long id);
    Article save(Article article);
    void deleteById(Long id);
}
