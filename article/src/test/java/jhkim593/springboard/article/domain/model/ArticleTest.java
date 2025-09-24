package jhkim593.springboard.article.domain.model;

import jhkim593.springboard.article.common.ArticleDataFactory;
import jhkim593.springboard.article.domain.dto.ArticleRegisterDto;
import jhkim593.springboard.article.domain.dto.ArticleUpdateDto;
import jhkim593.springboard.common.core.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.core.event.payload.ArticleDeletedEventPayload;
import jhkim593.springboard.common.core.event.payload.ArticleRegisteredEventPayload;
import jhkim593.springboard.common.core.event.payload.ArticleUpdatedEventPayload;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleTest {

    @Test
    void create() {
        // given
        Long articleId = 1L;
        ArticleRegisterDto request = ArticleRegisterDto.builder()
                .title("test title")
                .content("test content")
                .boardId(100L)
                .writerId(200L)
                .build();

        // when
        Article article = Article.create(articleId, request);

        // then
        assertThat(article.getArticleId()).isEqualTo(articleId);
        assertThat(article.getTitle()).isEqualTo("test title");
        assertThat(article.getContent()).isEqualTo("test content");
        assertThat(article.getBoardId()).isEqualTo(100L);
        assertThat(article.getWriterId()).isEqualTo(200L);
        assertThat(article.getDeleted()).isFalse();
        assertThat(article.getCreatedAt()).isNotNull();
        assertThat(article.getModifiedAt()).isNotNull();
    }

    @Test
    void update() throws InterruptedException {
        // given
        Article article = ArticleDataFactory.createTestArticle();
        LocalDateTime originalModifiedAt = article.getModifiedAt();
        
        ArticleUpdateDto updateRequest = ArticleUpdateDto.builder()
                .title("updated title")
                .content("updated content")
                .build();

        Thread.sleep(1000l);

        // when
        article.update(updateRequest);

        // then
        assertThat(article.getTitle()).isEqualTo("updated title");
        assertThat(article.getContent()).isEqualTo("updated content");
        assertThat(article.getModifiedAt()).isAfter(originalModifiedAt);
    }

    @Test
    void delete() {
        // given
        Article article = ArticleDataFactory.createTestArticle();

        // when
        article.delete();

        // then
        assertThat(article.getDeleted()).isTrue();
    }

    @Test
    void createRegisteredEventPayload() {
        // given
        Article article = ArticleDataFactory.createTestArticle();
        Long boardArticleCount = 50L;

        // when
        ArticleRegisteredEventPayload payload = article.createRegisteredEventPayload(boardArticleCount);

        // then
        assertThat(payload.getArticleId()).isEqualTo(article.getArticleId());
        assertThat(payload.getTitle()).isEqualTo(article.getTitle());
        assertThat(payload.getContent()).isEqualTo(article.getContent());
        assertThat(payload.getBoardId()).isEqualTo(article.getBoardId());
        assertThat(payload.getWriterId()).isEqualTo(article.getWriterId());
        assertThat(payload.getBoardArticleCount()).isEqualTo(boardArticleCount);
    }

    @Test
    void createDeletedEventPayload() {
        // given
        Article article = ArticleDataFactory.createTestArticle();
        Long boardArticleCount = 30L;

        // when
        ArticleDeletedEventPayload payload = article.createDeletedEventPayload(boardArticleCount);

        // then
        assertThat(payload.getArticleId()).isEqualTo(article.getArticleId());
        assertThat(payload.getBoardId()).isEqualTo(article.getBoardId());
        assertThat(payload.getWriterId()).isEqualTo(article.getWriterId());
        assertThat(payload.getBoardArticleCount()).isEqualTo(boardArticleCount);
    }

    @Test
    void createUpdatedEventPayload() {
        // given
        Article article = ArticleDataFactory.createTestArticle();

        // when
        ArticleUpdatedEventPayload payload = article.createUpdatedEventPayload();

        // then
        assertThat(payload.getArticleId()).isEqualTo(article.getArticleId());
        assertThat(payload.getTitle()).isEqualTo(article.getTitle());
        assertThat(payload.getContent()).isEqualTo(article.getContent());
        assertThat(payload.getBoardId()).isEqualTo(article.getBoardId());
        assertThat(payload.getWriterId()).isEqualTo(article.getWriterId());
    }

    @Test
    void createDetailDto() {
        // given
        Article article = ArticleDataFactory.createTestArticle();

        // when
        ArticleDetailDto dto = article.createDetailDto();

        // then
        assertThat(dto.getArticleId()).isEqualTo(article.getArticleId());
        assertThat(dto.getTitle()).isEqualTo(article.getTitle());
        assertThat(dto.getContent()).isEqualTo(article.getContent());
        assertThat(dto.getBoardId()).isEqualTo(article.getBoardId());
        assertThat(dto.getCreatedAt()).isEqualTo(article.getCreatedAt());
        assertThat(dto.getModifiedAt()).isEqualTo(article.getModifiedAt());
    }
}