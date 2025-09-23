package jhkim593.springboard.common.core.articleread.application.required.repository;

public interface BoardArticleCountRepository {
    void update(Long boardId, Long articleCount);

    Long count(Long boardId);

    String generateKey(Long boardId);
}
