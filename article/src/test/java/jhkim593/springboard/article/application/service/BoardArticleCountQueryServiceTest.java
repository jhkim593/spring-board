package jhkim593.springboard.article.application.service;

import jhkim593.springboard.article.adapter.persistence.jpa.BoardArticleCountJpaRepository;
import jhkim593.springboard.article.application.required.repository.BoardArticleCountRepository;
import jhkim593.springboard.article.common.DBCleanManager;
import jhkim593.springboard.article.common.TestConfig;
import jhkim593.springboard.article.domain.model.BoardArticleCount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestConfig.class)
class BoardArticleCountQueryServiceTest {

    @Autowired
    private BoardArticleCountJpaRepository boardArticleCountRepository;

    @Autowired
    private BoardArticleCountQueryService boardArticleCountQueryService;

    @Autowired
    private DBCleanManager cleanManager;

    @AfterEach
    public void clear() {
        cleanManager.execute();
    }

    @Test
    void count_보드가_존재할때() {
        // given
        Long boardId = 100L;
        BoardArticleCount boardArticleCount = BoardArticleCount.create(boardId);
        boardArticleCountRepository.save(boardArticleCount);

        // when
        Long result = boardArticleCountQueryService.count(boardId);

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    void count_보드가_존재하지않을때() {
        // given
        Long nonExistentBoardId = 999L;

        // when
        Long result = boardArticleCountQueryService.count(nonExistentBoardId);

        // then
        assertThat(result).isEqualTo(0L);
    }

    @Test
    void count_카운트가_0인_보드() {
        // given
        Long boardId = 200L;
        BoardArticleCount boardArticleCount = BoardArticleCount.create(boardId);
        boardArticleCount.decrease();
        boardArticleCountRepository.save(boardArticleCount);

        // when
        Long result = boardArticleCountQueryService.count(boardId);

        // then
        assertThat(result).isEqualTo(0L);
    }
}