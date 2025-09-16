package jhkim593.springboard.article.application;

import jhkim593.springboard.article.application.required.repository.ArticleRepository;
import jhkim593.springboard.article.application.required.repository.BoardArticleCountRepository;
import jhkim593.springboard.article.common.ArticleDataFactory;
import jhkim593.springboard.article.common.DBCleanManager;
import jhkim593.springboard.article.common.TestConfig;
import jhkim593.springboard.article.domain.Article;
import jhkim593.springboard.article.domain.BoardArticleCount;
import jhkim593.springboard.article.domain.error.ErrorCode;
import jhkim593.springboard.common.dto.article.ArticlePageDto;
import jhkim593.springboard.common.error.CustomException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(TestConfig.class)
class ArticleQueryServiceTest {

    @Autowired
    private ArticleQueryService articleQueryService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private BoardArticleCountRepository boardArticleCountRepository;

    @Autowired
    private DBCleanManager cleanManager;

    @AfterEach
    public void clear() {
        cleanManager.execute();
    }

    @Test
    void findById_존재할때() {
        // given
        Long articleId = 100L;
        Article article = ArticleDataFactory.createTestArticle(1L, articleId);
        articleRepository.save(article);

        // when
        Article result = articleQueryService.findById(articleId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getArticleId()).isEqualTo(articleId);
        assertThat(result.getTitle()).isEqualTo(article.getTitle());
        assertThat(result.getContent()).isEqualTo(article.getContent());
    }

    @Test
    void findById_존재하지않으면_예외() {
        // given
        Long nonExistentId = 999L;

        // when & then
        assertThatThrownBy(() -> articleQueryService.findById(nonExistentId))
                .isInstanceOf(CustomException.class)
                .satisfies(ex -> {
                    CustomException customException = (CustomException) ex;
                    assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.ARTICLE_NOT_FOUNT);
                });
    }

    @Test
    void findArticlePage_정상_조회() {
        // given
        Long boardId = 100L;

        BoardArticleCount boardArticleCount = BoardArticleCount.create(boardId);
        boardArticleCount.increase(); // 2개
        boardArticleCount.increase(); // 3개
        boardArticleCountRepository.save(boardArticleCount);

        // 3개 아티클 저장
        for (long i = 1; i <= 3; i++) {
            Article article = ArticleDataFactory.createTestArticle(boardId, i);
            articleRepository.save(article);
        }

        Long pageNo = 0L;
        Long pageSize = 2L;

        // when
        ArticlePageDto result = articleQueryService.findArticlePage(boardId, pageNo, pageSize);

        // then
        assertThat(result.getData()).hasSize(2);
        assertThat(result.getTotalCount()).isEqualTo(3L);

        assertThat(result.getData().get(0).getArticleId()).isEqualTo(3L);
        assertThat(result.getData().get(1).getArticleId()).isEqualTo(2L);
    }

    @Test
    void findArticlePage_빈_페이지() {
        // given
        Long boardId = 200L;
        Long pageNo = 0L;
        Long pageSize = 10L;

        // when
        ArticlePageDto result = articleQueryService.findArticlePage(boardId, pageNo, pageSize);

        // then
        assertThat(result.getData()).isEmpty();
    }

    @Test
    void findArticlePage_존재하지않는_보드() {
        // given
        Long nonExistentBoardId = 999L;
        Long pageNo = 0L;
        Long pageSize = 10L;

        // when
        ArticlePageDto result = articleQueryService.findArticlePage(nonExistentBoardId, pageNo, pageSize);

        // then
        assertThat(result.getData()).isEmpty();
    }
}