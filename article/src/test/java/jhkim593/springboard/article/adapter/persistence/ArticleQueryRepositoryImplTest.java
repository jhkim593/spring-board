package jhkim593.springboard.article.adapter.persistence;

import jhkim593.springboard.article.application.required.repository.ArticleRepository;
import jhkim593.springboard.article.common.DBCleanManager;
import jhkim593.springboard.article.common.ArticleDataFactory;
import jhkim593.springboard.article.common.TestConfig;
import jhkim593.springboard.article.domain.Article;
import jhkim593.springboard.common.dto.article.ArticleDetailDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestConfig.class)
class ArticleQueryRepositoryImplTest {

    @Autowired
    ArticleQueryRepositoryImpl articleQueryRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    DBCleanManager cleanManager;

    @AfterEach
    public void clear(){
        cleanManager.execute();
    }

    @Test
    void findArticlePage_페이징_조회() {
        // given
        Long boardId = 100L;

        // 5개 게시글 저장
        for (long i = 1; i <= 5; i++) {
            Article article = ArticleDataFactory.createTestArticle(boardId, i);
            articleRepository.save(article);
        }

        Long pageNo = 0L;
        Long pageSize = 3L;

        // when
        List<ArticleDetailDto> result = articleQueryRepository.findArticlePage(boardId, pageNo, pageSize);

        // then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getBoardId()).isEqualTo(boardId);
        assertThat(result.get(0).getArticleId()).isEqualTo(5L);

        assertThat(result.get(1).getBoardId()).isEqualTo(boardId);
        assertThat(result.get(1).getArticleId()).isEqualTo(4L);

        assertThat(result.get(2).getBoardId()).isEqualTo(boardId);
        assertThat(result.get(2).getArticleId()).isEqualTo(3L);
    }

    @Test
    void findArticlePage_두번째페이지_조회() {
        // given
        Long boardId = 100L;

        // 5개 게시글 저장
        for (long i = 1; i <= 5; i++) {
            Article article = ArticleDataFactory.createTestArticle(boardId, i);
            articleRepository.save(article);
        }

        Long pageNo = 1L;
        Long pageSize = 3L;

        // when
        List<ArticleDetailDto> result = articleQueryRepository.findArticlePage(boardId, pageNo, pageSize);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getBoardId()).isEqualTo(boardId);
        assertThat(result.get(0).getArticleId()).isEqualTo(2L);

        assertThat(result.get(1).getBoardId()).isEqualTo(boardId);
        assertThat(result.get(1).getArticleId()).isEqualTo(1L);
    }

    @Test
    void findArticlePage_존재하지않는_boardId() {
        // given
        Long nonExistentBoardId = 999L;
        Long pageNo = 0L;
        Long pageSize = 10L;

        // when
        List<ArticleDetailDto> result = articleQueryRepository.findArticlePage(nonExistentBoardId, pageNo, pageSize);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void findArticlePage_삭제된_게시글_제외() {
        // given
        Long boardId = 100L;

        // 정상 게시글 2개
        Article article1 = ArticleDataFactory.createTestArticle(boardId, 1L);
        Article article2 = ArticleDataFactory.createTestArticle(boardId, 2L);
        Article article3 = ArticleDataFactory.createTestArticle(boardId, 3L);

        // 삭제된 게시글 1개
        Article deletedArticle = ArticleDataFactory.createTestArticle(boardId, 4L, true);

        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(deletedArticle);

        Long pageNo = 1L;
        Long pageSize = 2L;

        // when
        List<ArticleDetailDto> result = articleQueryRepository.findArticlePage(boardId, pageNo, pageSize);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBoardId()).isEqualTo(boardId);
        assertThat(result.get(0).getArticleId()).isEqualTo(1L);
    }
}