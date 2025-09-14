package jhkim593.springboard.article.application;

import jhkim593.springboard.article.common.data.ArticleDataFactory;
import jhkim593.springboard.article.domain.Article;
import jhkim593.springboard.article.domain.error.ErrorCode;
import jhkim593.springboard.article.fake.FakeArticleQueryRepository;
import jhkim593.springboard.article.fake.FakeArticleRepository;
import jhkim593.springboard.article.fake.FakeBoardArticleCountFinder;
import jhkim593.springboard.common.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.dto.article.ArticlePageDto;
import jhkim593.springboard.common.error.CustomException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;

class ArticleQueryServiceTest {

    private ArticleQueryService articleQueryService;
    private FakeArticleQueryRepository fakeArticleQueryRepository;
    private FakeArticleRepository fakeArticleRepository;
    private FakeBoardArticleCountFinder fakeBoardArticleCountFinder;

    @BeforeEach
    void setUp() {
        fakeArticleQueryRepository = new FakeArticleQueryRepository();
        fakeArticleRepository = new FakeArticleRepository();
        fakeBoardArticleCountFinder = new FakeBoardArticleCountFinder();

        articleQueryService = new ArticleQueryService(
                fakeArticleQueryRepository, 
                fakeArticleRepository, 
                fakeBoardArticleCountFinder
        );
    }

    @Test
    void findById_Article_존재하면_반환() {
        // given
        Long articleId = 1L;
        Article testArticle = ArticleDataFactory.createTestArticle();
        fakeArticleRepository.addArticle(testArticle);

        // when
        Article result = articleQueryService.findById(articleId);

        // then
        assertThat(result.getArticleId()).isEqualTo(articleId);
        assertThat(result.getTitle()).isEqualTo("test title");
        assertThat(result.getContent()).isEqualTo("test content");
        assertThat(result.getBoardId()).isEqualTo(1L);
        assertThat(result.getWriterId()).isEqualTo(1L);
    }

    @Test
    void findById_존재하지않으면_예외() {
        // given
        Long nonExistentId = 999L;

        //when
        CustomException exception = catchThrowableOfType(
                () -> articleQueryService.findById(nonExistentId), CustomException.class);

        // then
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ARTICLE_NOT_FOUNT);
    }

    @Test
    void findArticlePage() {
        // given
        Long boardId = 100L;
        Long pageNo = 1L;
        Long pageSize = 3L;
        
        List<ArticleDetailDto> testArticles = new ArrayList<>();
        for (long i = 1; i <= 5; i++) {
            testArticles.add(ArticleDataFactory.createTestArticle(boardId,i).createDetailDto());
        }
        fakeArticleQueryRepository.addArticles(boardId, testArticles);
        fakeBoardArticleCountFinder.setBoardCount(boardId, 5L);

        // when
        ArticlePageDto pageDto = articleQueryService.findArticlePage(boardId, pageNo, pageSize);

        // then
        assertThat(pageDto.getData()).hasSize(3);
        assertThat(pageDto.getTotalCount()).isEqualTo(5L);
        assertThat(pageDto.getData().get(0).getArticleId()).isEqualTo(1L);
        assertThat(pageDto.getData().get(1).getArticleId()).isEqualTo(2L);
        assertThat(pageDto.getData().get(2).getArticleId()).isEqualTo(3L);
    }

    @Test
    void findArticlePage_존재하지않을때() {
        // given
        Long boardId = 100L;
        Long pageNo = 1L;
        Long pageSize = 10L;

        // when
        ArticlePageDto pageDto = articleQueryService.findArticlePage(boardId, pageNo, pageSize);

        // then
        assertThat(pageDto.getData()).isEmpty();
        assertThat(pageDto.getTotalCount()).isEqualTo(0L);
    }

    @Test
    void findArticlePage_다음페이지있을때() {
        // given
        Long boardId = 100L;
        Long pageNo = 2L;
        Long pageSize = 2L;
        
        List<ArticleDetailDto> testArticles = new ArrayList<>();
        for (long i = 1; i <= 5; i++) {
            testArticles.add(FakeArticleQueryRepository.createTestArticleDetailDto(i, boardId));
        }
        fakeArticleQueryRepository.addArticles(boardId, testArticles);
        fakeBoardArticleCountFinder.setBoardCount(boardId, 5L);

        // when
        ArticlePageDto result = articleQueryService.findArticlePage(boardId, pageNo, pageSize);

        // then
        assertThat(result.getData()).hasSize(2);
        assertThat(result.getTotalCount()).isEqualTo(5L);
        assertThat(result.getData().get(0).getArticleId()).isEqualTo(3L);
        assertThat(result.getData().get(1).getArticleId()).isEqualTo(4L);
    }
}