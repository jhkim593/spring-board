package jhkim593.springboard.article.application.provided;

public interface BoardArticleCountUpdater {
    Long increase(Long boardId);
    Long decrease(Long boardId);
}
