package jhkim593.springboard.article.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardArticleCount {
    @Id
    private Long boardId;
    private Long articleCount;

    public static BoardArticleCount create(Long boardId) {
        BoardArticleCount boardArticleCount = new BoardArticleCount();
        boardArticleCount.boardId = boardId;
        boardArticleCount.articleCount = 1L;
        return boardArticleCount;
    }
    public void increase() {
        this.articleCount++;
    }
    public void decrease() {
        this.articleCount--;
    }
}
