package jhkim593.springboard.article.application.service;

import jhkim593.springboard.article.adapter.event.SpringEventListener;
import jhkim593.springboard.article.adapter.persistence.jpa.ArticleJpaRepository;
import jhkim593.springboard.article.adapter.persistence.jpa.BoardArticleCountJpaRepository;
import jhkim593.springboard.article.application.required.repository.EventRepository;
import jhkim593.springboard.article.common.ArticleDataFactory;
import jhkim593.springboard.article.common.DBCleanManager;
import jhkim593.springboard.article.common.TestConfig;
import jhkim593.springboard.article.domain.dto.ArticleRegisterDto;
import jhkim593.springboard.article.domain.dto.ArticleUpdateDto;
import jhkim593.springboard.article.domain.error.ErrorCode;
import jhkim593.springboard.article.domain.event.ArticleDeletedEvent;
import jhkim593.springboard.article.domain.event.ArticleEvent;
import jhkim593.springboard.article.domain.event.ArticleRegisteredEvent;
import jhkim593.springboard.article.domain.event.ArticleUpdatedEvent;
import jhkim593.springboard.article.domain.model.Article;
import jhkim593.springboard.article.domain.model.BoardArticleCount;
import jhkim593.springboard.common.error.CustomException;
import jhkim593.springboard.common.event.EventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@Import(TestConfig.class)
class ArticleUpdateServiceTransactionTest {

    @Autowired
    private ArticleUpdateService articleUpdateService;

    @Autowired
    private ArticleJpaRepository articleRepository;

    @Autowired
    private BoardArticleCountJpaRepository boardArticleCountRepository;

    @Autowired
    private DBCleanManager cleanManager;

    @Autowired
    private EventRepository eventRepository;

    @MockitoSpyBean
    private SpringEventListener eventListener;

    @AfterEach
    public void clear() {
        cleanManager.execute();
    }

    @Test
    void register_새로운_아티클_등록() {
        // given
        ArticleRegisterDto registerDto = ArticleDataFactory.createTestArticleRegisterDto();

        // when
        Article result = articleUpdateService.register(registerDto);

        // then
        // BoardArticleCount 증가 확인
        Optional<BoardArticleCount> boardCount = boardArticleCountRepository.findById(registerDto.getBoardId());
        assertThat(boardCount.get().getArticleCount()).isEqualTo(1L);

        // Article 생성 확인
        Optional<Article> article = articleRepository.findById(result.getArticleId());
        assertThat(article).isPresent();
        assertThat(article.get().getTitle()).isEqualTo(registerDto.getTitle());
        assertThat(article.get().getContent()).isEqualTo(registerDto.getContent());
        assertThat(article.get().getWriterId()).isEqualTo(registerDto.getWriterId());
        assertThat(article.get().getBoardId()).isEqualTo(registerDto.getBoardId());
        assertThat(article.get().getDeleted()).isFalse();

        // 이벤트 저장 확인
        Optional<ArticleEvent> latestEvent = eventRepository.findTopByOrderByCreatedAtDesc();
        assertThat(latestEvent).isPresent();
        ArticleEvent event = latestEvent.get();
        assertThat(event.getEventType()).isEqualTo(EventType.ARTICLE_REGISTERED);
        assertThat(event.getArticleId()).isEqualTo(result.getArticleId());
        assertThat(event.isPublished()).isFalse();
    }

    @Test
    void register_기존_보드에_아티클_추가() {
        // given
        Long boardId = 200L;

        // 기존 보드 카운트 설정
        BoardArticleCount existingCount = BoardArticleCount.create(boardId);
        existingCount.increase(); // 2개
        boardArticleCountRepository.save(existingCount);

        ArticleRegisterDto registerDto = ArticleDataFactory.createTestArticleRegisterDto(boardId);

        // when
        Article result = articleUpdateService.register(registerDto);

        // then
        // BoardArticleCount 증가 확인 (2 -> 3)
        Optional<BoardArticleCount> boardCount = boardArticleCountRepository.findById(boardId);
        assertThat(boardCount).isPresent();
        assertThat(boardCount.get().getArticleCount()).isEqualTo(3L);

        // Article 생성 확인
        Optional<Article> article = articleRepository.findById(result.getArticleId());
        assertThat(article).isPresent();
        assertThat(article.get().getTitle()).isEqualTo(registerDto.getTitle());
        assertThat(article.get().getContent()).isEqualTo(registerDto.getContent());
        assertThat(article.get().getWriterId()).isEqualTo(registerDto.getWriterId());
        assertThat(article.get().getBoardId()).isEqualTo(registerDto.getBoardId());
        assertThat(article.get().getDeleted()).isFalse();

        // 이벤트 저장 확인
        Optional<ArticleEvent> latestEvent = eventRepository.findTopByOrderByCreatedAtDesc();
        assertThat(latestEvent).isPresent();
        ArticleEvent event = latestEvent.get();
        assertThat(event.getEventType()).isEqualTo(EventType.ARTICLE_REGISTERED);
        assertThat(event.getArticleId()).isEqualTo(result.getArticleId());
    }

    @Test
    void update_기존_아티클_수정() {
        // given
        Long articleId = 100L;
        Article article = ArticleDataFactory.createTestArticle(1L, articleId);
        articleRepository.save(article);

        ArticleUpdateDto updateDto = ArticleUpdateDto.builder()
                .title("title")
                .content("content")
                .build();

        // when
        articleUpdateService.update(articleId, updateDto);

        // then
        Optional<Article> found = articleRepository.findById(articleId);
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(found.get().getContent()).isEqualTo(updateDto.getContent());

        // 이벤트 저장 확인
        Optional<ArticleEvent> latestEvent = eventRepository.findTopByOrderByCreatedAtDesc();
        assertThat(latestEvent).isPresent();
        ArticleEvent event = latestEvent.get();
        assertThat(event.getEventType()).isEqualTo(EventType.ARTICLE_UPDATED);
        assertThat(event.getArticleId()).isEqualTo(articleId);
    }

    @Test
    void update_존재하지않는_아티클() {
        // given
        Long nonExistentArticleId = 999L;
        ArticleUpdateDto updateDto = ArticleUpdateDto.builder()
                .title("title")
                .content("content")
                .build();

        // when & then
        assertThatThrownBy(() -> articleUpdateService.update(nonExistentArticleId, updateDto))
                .isInstanceOf(CustomException.class)
                .satisfies(ex -> {
                    CustomException customException = (CustomException) ex;
                    assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.ARTICLE_NOT_FOUNT);
                });
    }

    @Test
    void delete_기존_아티클_삭제() {
        // given
        Long boardId = 300L;
        Long articleId = 100L;

        // 보드 카운트 설정
        BoardArticleCount boardCount = BoardArticleCount.create(boardId);
        boardCount.increase(); // 2개
        boardArticleCountRepository.save(boardCount);

        Article article = ArticleDataFactory.createTestArticle(boardId, articleId);
        articleRepository.save(article);

        // when
        articleUpdateService.delete(articleId);

        // then
        Optional<Article> found = articleRepository.findById(articleId);
        assertThat(found).isPresent();
        assertThat(found.get().getDeleted()).isTrue();

        // BoardArticleCount 감소 확인 (2 -> 1)
        Optional<BoardArticleCount> updatedBoardCount = boardArticleCountRepository.findById(boardId);
        assertThat(updatedBoardCount).isPresent();
        assertThat(updatedBoardCount.get().getArticleCount()).isEqualTo(1L);

        // 이벤트 저장 확인
        Optional<ArticleEvent> latestEvent = eventRepository.findTopByOrderByCreatedAtDesc();
        assertThat(latestEvent).isPresent();
        ArticleEvent event = latestEvent.get();
        assertThat(event.getEventType()).isEqualTo(EventType.ARTICLE_DELETED);
        assertThat(event.getArticleId()).isEqualTo(articleId);
    }

    @Test
    void delete_존재하지않는_아티클() {
        // given
        Long nonExistentArticleId = 999L;

        // when & then
        assertThatThrownBy(() -> articleUpdateService.delete(nonExistentArticleId))
                .isInstanceOf(CustomException.class)
                .satisfies(ex -> {
                    CustomException customException = (CustomException) ex;
                    assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.ARTICLE_NOT_FOUNT);
                });
    }


    @Test
    void register_이벤트_저장_실패시_전체_롤백() {
        // given
        Long boardId = 100L;
        ArticleRegisterDto registerDto = ArticleDataFactory.createTestArticleRegisterDto();

        // SpyBean을 사용하여 이벤트 리스너에서 실패하도록 설정
        doThrow(new RuntimeException())
                .when(eventListener).registerEvent(any(ArticleRegisteredEvent.class));

        // when & then
        assertThatThrownBy(() -> articleUpdateService.register(registerDto))
                .isInstanceOf(RuntimeException.class);

        // 트랜잭션이 롤백되어 데이터가 저장되지 않았는지 확인
        List<Article> articles = articleRepository.findAll();
        assertThat(articles.size()).isEqualTo(0);

        Optional<BoardArticleCount> boardArticleCount = boardArticleCountRepository.findById(boardId);
        assertThat(boardArticleCount.isPresent()).isFalse();
    }

    @Test
    void update_이벤트_저장_실패시_전체_롤백() {
        // given
        Long articleId = 100L;
        Article article = ArticleDataFactory.createTestArticle(1L, articleId);
        articleRepository.save(article);

        ArticleUpdateDto updateDto = ArticleUpdateDto.builder()
                .title("update title")
                .content("update content")
                .build();

        // 이벤트 처리 실패 설정
        doThrow(new RuntimeException())
                .when(eventListener).registerEvent(any(ArticleUpdatedEvent.class));

        // when & then
        assertThatThrownBy(() -> articleUpdateService.update(articleId, updateDto))
                .isInstanceOf(RuntimeException.class);

        // 업데이트가 롤백되어 원래 데이터가 유지되는지 확인
        Article foundArticle = articleRepository.findById(articleId).get();
        assertThat(foundArticle.getTitle()).isNotEqualTo(updateDto.getTitle());
        assertThat(foundArticle.getContent()).isNotEqualTo(updateDto.getContent());
    }

    @Test
    void delete_이벤트_저장_실패시_전체_롤백() {
        // given
        Long boardId = 100L;
        Long articleId = 200L;

        // 보드 카운트 설정
        BoardArticleCount boardCount = BoardArticleCount.create(boardId);
        boardCount.increase(); // 2개
        boardArticleCountRepository.save(boardCount);

        Article article = ArticleDataFactory.createTestArticle(boardId, articleId);
        articleRepository.save(article);

        // 이벤트 처리 실패 설정
        doThrow(new RuntimeException())
                .when(eventListener).registerEvent(any(ArticleDeletedEvent.class));

        // when & then
        assertThatThrownBy(() -> articleUpdateService.delete(articleId))
                .isInstanceOf(RuntimeException.class);

        // 삭제가 롤백되어 데이터가 유지되는지 확인
        Article foundArticle = articleRepository.findById(articleId).get();
        assertThat(foundArticle.getDeleted()).isFalse(); // 삭제되지 않음

        // 보드 카운트도 원래대로 유지
        BoardArticleCount foundBoardCount = boardArticleCountRepository.findById(boardId).orElseThrow();
        assertThat(foundBoardCount.getArticleCount()).isEqualTo(2L); // 감소하지 않음
    }

}