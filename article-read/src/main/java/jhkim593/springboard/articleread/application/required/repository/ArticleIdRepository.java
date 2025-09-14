package jhkim593.springboard.articleread.application.required.repository;

import java.util.List;

public interface ArticleIdRepository {
    void add(Long boardId, Long articleId);

    void delete(Long boardId, Long articleId);

    List<Long> readAll(Long boardId, Long offset, Long limit);

    List<Long> readAllInfiniteScroll(Long boardId, Long lastArticleId, Long limit);
}
