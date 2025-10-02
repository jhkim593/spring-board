package jhkim593.springboard.article.application.service;

import jhkim593.springboard.article.adapter.persistence.jpa.ArticleJpaRepository;
import jhkim593.springboard.article.adapter.persistence.jpa.BoardArticleCountJpaRepository;
import jhkim593.springboard.article.common.ArticleDataFactory;
import jhkim593.springboard.article.domain.error.ErrorCode;
import jhkim593.springboard.article.domain.model.Article;
import jhkim593.springboard.article.domain.model.BoardArticleCount;
import jhkim593.springboard.common.core.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.core.dto.article.ArticlePageDto;
import jhkim593.springboard.common.core.error.CustomException;
import jhkim593.springboard.common.test.DBCleanManager;
import jhkim593.springboard.common.test.TestConfig;
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
    private ArticleJpaRepository articleRepository;

    @Autowired
    private BoardArticleCountJpaRepository boardArticleCountRepository;

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
        ArticleDetailDto result = articleQueryService.findById(articleId);

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

        Long pageNo = 1L;
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
        Long pageNo = 1L;
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
        Long pageNo = 1L;
        Long pageSize = 10L;

        // when
        ArticlePageDto result = articleQueryService.findArticlePage(nonExistentBoardId, pageNo, pageSize);

        // then
        assertThat(result.getData()).isEmpty();
    }
}