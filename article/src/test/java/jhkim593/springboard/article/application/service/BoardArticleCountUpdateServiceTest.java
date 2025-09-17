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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(TestConfig.class)
class BoardArticleCountUpdateServiceTest {

    @Autowired
    private BoardArticleCountJpaRepository boardArticleCountRepository;

    @Autowired
    private BoardArticleCountUpdateService boardArticleCountUpdateService;

    @Autowired
    private DBCleanManager cleanManager;

    @AfterEach
    public void clear() {
        cleanManager.execute();
    }

    @Test
    void increase_기존_보드가_존재할때() {
        // given
        Long boardId = 100L;
        BoardArticleCount boardArticleCount = BoardArticleCount.create(boardId);
        boardArticleCountRepository.save(boardArticleCount);

        // when
        Long result = boardArticleCountUpdateService.increase(boardId);

        // then
        assertThat(result).isEqualTo(2L);

        Optional<BoardArticleCount> found = boardArticleCountRepository.findById(boardId);
        assertThat(found).isPresent();
        assertThat(found.get().getArticleCount()).isEqualTo(2L);
    }

    @Test
    void increase_보드가_존재하지않을때() {
        // given
        Long boardId = 200L;

        // when
        Long result = boardArticleCountUpdateService.increase(boardId);

        // then
        assertThat(result).isEqualTo(1L);

        Optional<BoardArticleCount> found = boardArticleCountRepository.findById(boardId);
        assertThat(found).isPresent();
        assertThat(found.get().getArticleCount()).isEqualTo(1L);
        assertThat(found.get().getBoardId()).isEqualTo(boardId);
    }

    @Test
    void decrease_기존_보드가_존재할때() {
        // given
        Long boardId = 300L;
        BoardArticleCount boardArticleCount = BoardArticleCount.create(boardId);
        boardArticleCount.increase(); // articleCount = 2
        boardArticleCountRepository.save(boardArticleCount);

        // when
        Long result = boardArticleCountUpdateService.decrease(boardId);

        // then
        assertThat(result).isEqualTo(1L);

        Optional<BoardArticleCount> found = boardArticleCountRepository.findById(boardId);
        assertThat(found).isPresent();
        assertThat(found.get().getArticleCount()).isEqualTo(1L);
    }

    @Test
    void decrease_보드가_존재하지않을때() {
        // given
        Long boardId = 400L;

        // when
        Long result = boardArticleCountUpdateService.decrease(boardId);

        //then
        assertThat(result).isEqualTo(0L);
    }
}