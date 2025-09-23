package jhkim593.springboard.common.core.articleread.application;

import jhkim593.springboard.common.core.articleread.application.required.repository.ArticleIdRepository;
import jhkim593.springboard.common.core.articleread.application.required.repository.ArticleReadRepository;
import jhkim593.springboard.common.core.articleread.application.required.repository.BoardArticleCountRepository;
import jhkim593.springboard.common.core.articleread.common.ArticleReadDataFactory;
import jhkim593.springboard.common.core.articleread.common.RedisCleanManager;
import jhkim593.springboard.common.core.articleread.common.TestConfig;
import jhkim593.springboard.common.core.articleread.domain.ArticleRead;
import jhkim593.springboard.common.core.articleread.domain.dto.ArticleReadDetailDto;
import jhkim593.springboard.common.core.articleread.domain.dto.ArticleReadPageDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestConfig.class)
class ArticleReadQueryServiceTest {

    @Autowired
    private ArticleReadQueryService articleReadQueryService;

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
    void read_정상_조회() {
        // given
        Long articleId = 100L;
        Long boardId = 1L;
        ArticleRead articleRead = ArticleReadDataFactory.createTestArticleRead(boardId, articleId);
        articleReadRepository.create(articleRead);

        // when
        ArticleReadDetailDto result = articleReadQueryService.read(articleId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getArticleId()).isEqualTo(articleId);
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getContent()).isEqualTo("content");
        assertThat(result.getBoardId()).isEqualTo(boardId);
    }

    @Test
    void readPage_정상_조회() {
        // given
        Long boardId = 1L;
        Long page = 1L;
        Long pageSize = 2L;

        // 테스트 데이터 생성
        ArticleRead article1 = ArticleReadDataFactory.createTestArticleRead(boardId, 1L);
        ArticleRead article2 = ArticleReadDataFactory.createTestArticleRead(boardId, 2L);
        ArticleRead article3 = ArticleReadDataFactory.createTestArticleRead(boardId, 3L);

        articleReadRepository.create(article1);
        articleReadRepository.create(article2);
        articleReadRepository.create(article3);

        // 게시판별 아티클 ID 목록 저장
        for (long i = 1; i <=3; i++) {
            articleIdRepository.add(boardId, i);
        }

        // 게시판 아티클 수 저장
        boardArticleCountRepository.update(boardId, 3L);

        // when
        ArticleReadPageDto result = articleReadQueryService.readPage(boardId, page, pageSize);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getArticles()).hasSize(2);
        assertThat(result.getArticleCount()).isEqualTo(3L);

        assertThat(result.getArticles().get(0).getArticleId()).isEqualTo(3L);
        assertThat(result.getArticles().get(1).getArticleId()).isEqualTo(2L);
    }

    @Test
    void readPage_정상_조회_두번째페이지조회() {
        // given
        Long boardId = 1L;
        Long page = 2L;
        Long pageSize = 2L;

        // 테스트 데이터 생성
        ArticleRead article1 = ArticleReadDataFactory.createTestArticleRead(boardId, 1L);
        ArticleRead article2 = ArticleReadDataFactory.createTestArticleRead(boardId, 2L);
        ArticleRead article3 = ArticleReadDataFactory.createTestArticleRead(boardId, 3L);

        articleReadRepository.create(article1);
        articleReadRepository.create(article2);
        articleReadRepository.create(article3);

        // 게시판별 아티클 ID 목록 저장
        for (long i = 1; i <=3; i++) {
            articleIdRepository.add(boardId, i);
        }

        // 게시판 아티클 수 저장
        boardArticleCountRepository.update(boardId, 3L);

        // when
        ArticleReadPageDto result = articleReadQueryService.readPage(boardId, page, pageSize);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getArticles()).hasSize(1);
        assertThat(result.getArticleCount()).isEqualTo(3L);

        // 최신순으로 정렬되어야 함
        assertThat(result.getArticles().get(0).getArticleId()).isEqualTo(1L);
    }

    @Test
    void readPage_빈_결과() {
        // given
        Long boardId = 999L;
        Long page = 1L;
        Long pageSize = 10L;

        // when
        ArticleReadPageDto result = articleReadQueryService.readPage(boardId, page, pageSize);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getArticles()).isEmpty();
        assertThat(result.getArticleCount()).isEqualTo(0L);
    }
}