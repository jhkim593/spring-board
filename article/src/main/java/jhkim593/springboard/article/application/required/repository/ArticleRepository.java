package jhkim593.springboard.article.application.required.repository;

import jhkim593.springboard.article.domain.model.Article;
import jhkim593.springboard.common.core.dto.article.ArticleDetailDto;

import java.util.List;

public interface ArticleRepository {
    Article findById(Long id);
    Article save(Article article);
    List<ArticleDetailDto> find(Long boardId, Long offset, Long limit);
}
