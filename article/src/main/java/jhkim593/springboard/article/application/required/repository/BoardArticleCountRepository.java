package jhkim593.springboard.article.application.required.repository;

import jhkim593.springboard.article.domain.BoardArticleCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardArticleCountRepository extends Repository<BoardArticleCount, Long> {
    @Query(
            value = "update board_article_count set article_count = article_count + 1 where board_id = :boardId",
            nativeQuery = true
    )
    @Modifying
    Long increase(@Param("boardId") Long boardId);

    @Query(
            value = "update board_article_count set article_count = article_count - 1 where board_id = :boardId",
            nativeQuery = true
    )
    @Modifying
    Long decrease(@Param("boardId") Long boardId);

    BoardArticleCount save(BoardArticleCount boardArticleCount);

    Optional<BoardArticleCount> findById(Long boardId);
}
