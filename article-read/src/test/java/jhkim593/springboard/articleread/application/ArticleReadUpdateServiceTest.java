package jhkim593.springboard.articleread.application;

import jhkim593.springboard.articleread.application.required.repository.ArticleIdRepository;
import jhkim593.springboard.articleread.application.required.repository.ArticleReadRepository;
import jhkim593.springboard.articleread.application.required.repository.BoardArticleCountRepository;
import jhkim593.springboard.articleread.common.ArticleReadDataFactory;
import jhkim593.springboard.articleread.common.RedisCleanManager;
import jhkim593.springboard.articleread.common.TestConfig;
import jhkim593.springboard.articleread.domain.ArticleRead;
import jhkim593.springboard.articleread.domain.error.ErrorCode;
import jhkim593.springboard.common.error.CustomException;
import jhkim593.springboard.common.event.payload.ArticleDeletedEventPayload;
import jhkim593.springboard.common.event.payload.ArticleRegisteredEventPayload;
import jhkim593.springboard.common.event.payload.ArticleUpdatedEventPayload;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(TestConfig.class)
class ArticleReadUpdateServiceTest {

    @Autowired
    private ArticleReadUpdateService articleReadUpdateService;

    @Autowired
    private ArticleReadRepository articleReadRepository;

    @Autowired
    private ArticleIdRepository articleIdRepository;

    @Autowired
    private BoardArticleCountRepository boardArticleCountRepository;

    @Autowired
    private RedisCleanManager cleanManager;

    @AfterEach
    public void clear() {
        cleanManager.execute();
    }

    @Test
    void create() {
        // given
        Long articleId = 1L;
        Long boardId = 1L;
        Long boardArticleCount = 5L;

        ArticleRegisteredEventPayload payload = ArticleRegisteredEventPayload.builder()
                .articleId(articleId)
                .title("Test Title")
                .content("Test Content")
                .boardId(boardId)
                .writerId(1L)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .boardArticleCount(boardArticleCount)
                .build();

        // when
        articleReadUpdateService.create(payload);

        // then
        ArticleRead savedArticleRead = articleReadRepository.read(articleId);
        assertThat(savedArticleRead).isNotNull();
        assertThat(savedArticleRead.getArticleId()).isEqualTo(articleId);
        assertThat(savedArticleRead.getTitle()).isEqualTo("Test Title");
        assertThat(savedArticleRead.getContent()).isEqualTo("Test Content");
        assertThat(savedArticleRead.getBoardId()).isEqualTo(boardId);

        // ArticleId 목록
        List<Long> articleIds = articleIdRepository.read(boardId, 0L, 10L);
        assertThat(articleIds).contains(articleId);

        // BoardArticleCount
        Long count = boardArticleCountRepository.count(boardId);
        assertThat(count).isEqualTo(boardArticleCount);
    }

    @Test
    void update() {
        // given
        Long articleId = 1L;
        Long boardId = 1L;

        ArticleRead articleRead  = ArticleReadDataFactory.createTestArticleRead(boardId, articleId);
        articleReadRepository.create(articleRead);
        articleIdRepository.add(boardId, articleId);
        boardArticleCountRepository.update(boardId, 1L);

        // 업데이트 페이로드
        ArticleUpdatedEventPayload updatePayload = ArticleUpdatedEventPayload.builder()
                .articleId(articleId)
                .title("Updated Title")
                .content("Updated Content")
                .boardId(boardId)
                .writerId(1L)
                .modifiedAt(LocalDateTime.now())
                .build();

        // when
        articleReadUpdateService.update(updatePayload);

        // then
        ArticleRead updatedArticleRead = articleReadRepository.read(articleId);
        assertThat(updatedArticleRead).isNotNull();
        assertThat(updatedArticleRead.getArticleId()).isEqualTo(articleId);
        assertThat(updatedArticleRead.getTitle()).isEqualTo("Updated Title");
        assertThat(updatedArticleRead.getContent()).isEqualTo("Updated Content");
        assertThat(updatedArticleRead.getBoardId()).isEqualTo(boardId);

        // ArticleId 목록 유지
        List<Long> articleIds = articleIdRepository.read(boardId, 0L, 10L);
        assertThat(articleIds.size()).isEqualTo(1);
        assertThat(articleIds).contains(articleId);
    }

    @Test
    void delete() {
        // given
        Long articleId = 1L;
        Long boardId = 1L;

        ArticleRead article = ArticleReadDataFactory.createTestArticleRead(boardId, articleId);
        articleReadRepository.create(article);
        articleIdRepository.add(boardId, articleId);
        boardArticleCountRepository.update(boardId, 1L);

        // 삭제 페이로드
        ArticleDeletedEventPayload deletePayload = ArticleDeletedEventPayload.builder()
                .articleId(articleId)
                .boardId(boardId)
                .boardArticleCount(0L)
                .build();

        // when
        articleReadUpdateService.delete(deletePayload);

        // then
        assertThatThrownBy(() -> articleReadRepository.read(articleId))
                .isInstanceOf(CustomException.class)
                .satisfies(ex -> {
                    CustomException customException = (CustomException) ex;
                    assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.ARTICLE_READ_NOT_FOUNT);
                });

        // ArticleId 목록 제거
        List<Long> articleIds = articleIdRepository.read(boardId, 0L, 10L);
        assertThat(articleIds.size()).isEqualTo(0);
        assertThat(articleIds).doesNotContain(articleId);

        // BoardArticleCount
        Long count = boardArticleCountRepository.count(boardId);
        assertThat(count).isEqualTo(0L);
    }
}