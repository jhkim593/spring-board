package jhkim593.springboard.article.application.required.repository;

import jhkim593.springboard.article.domain.model.Article;
import jhkim593.springboard.common.dto.article.ArticleDetailDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article findById(Long id);
    Article save(Article article);
    List<ArticleDetailDto> findArticlePage(Long boardId, Long pageNo, Long pageSize);
}
