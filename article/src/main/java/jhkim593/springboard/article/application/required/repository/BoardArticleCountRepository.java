package jhkim593.springboard.article.application.required.repository;

import jakarta.transaction.Transactional;
import jhkim593.springboard.article.domain.BoardArticleCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface BoardArticleCountRepository extends Repository<BoardArticleCount, Long> {
    @Query(
            value = "update board_article_count set article_count = article_count + 1 where board_id = :boardId",
            nativeQuery = true
    )
    @Modifying
    @Transactional
    int increase(@Param("boardId") Long boardId);

    @Query(
            value = "update board_article_count set article_count = article_count - 1 where board_id = :boardId",
            nativeQuery = true
    )
    @Modifying
    @Transactional
    int decrease(@Param("boardId") Long boardId);

    BoardArticleCount save(BoardArticleCount boardArticleCount);
    Optional<BoardArticleCount> findById(Long boardId);

}
