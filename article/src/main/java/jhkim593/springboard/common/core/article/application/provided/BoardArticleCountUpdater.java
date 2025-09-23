package jhkim593.springboard.common.core.article.application.provided;

public interface BoardArticleCountUpdater {
    Long increase(Long boardId);
    Long decrease(Long boardId);
}
