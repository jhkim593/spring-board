package jhkim593.springboard.comment.application.service;

import jhkim593.springboard.comment.adapter.persistence.jpa.CommentJpaRepository;
import jhkim593.springboard.comment.domain.dto.CommentRegisterDto;
import jhkim593.springboard.comment.domain.dto.CommentUpdateDto;
import jhkim593.springboard.comment.domain.error.ErrorCode;
import jhkim593.springboard.comment.domain.model.Comment;
import jhkim593.springboard.common.core.dto.comment.CommentDetailDto;
import jhkim593.springboard.common.core.error.CustomException;
import jhkim593.springboard.common.test.DBCleanManager;
import jhkim593.springboard.common.test.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static jhkim593.springboard.comment.common.CommentDataFactory.createTestComment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(TestConfig.class)
class CommentUpdateServiceTest {

    @Autowired
    private CommentUpdateService commentUpdateService;

    @Autowired
    private CommentJpaRepository commentJpaRepository;

    @Autowired
    private DBCleanManager dbCleanManager;

    @AfterEach
    public void clear() {
        dbCleanManager.execute();
    }

    @Test
    void 댓글등록_루트댓글() {
        // given
        CommentRegisterDto registerDto = new CommentRegisterDto();
        registerDto.setArticleId(1L);
        registerDto.setWriterId(100L);
        registerDto.setContent("댓글 내용");

        // when
        CommentDetailDto result = commentUpdateService.register(registerDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("댓글 내용");
        assertThat(result.getArticleId()).isEqualTo(1L);
        assertThat(result.getWriterId()).isEqualTo(100L);
        assertThat(result.getParentCommentId()).isEqualTo(result.getCommentId());
    }

    @Test
    void 댓글등록_대댓글() {
        // given
        Comment parent = createTestComment(1L, 1L, 1L);
        commentJpaRepository.save(parent);

        CommentRegisterDto registerDto = new CommentRegisterDto();
        registerDto.setArticleId(1L);
        registerDto.setWriterId(101L);
        registerDto.setContent("대댓글 내용");
        registerDto.setParentCommentId(1L);

        // when
        CommentDetailDto result = commentUpdateService.register(registerDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("대댓글 내용");
        assertThat(result.getParentCommentId()).isEqualTo(1L);
    }

    @Test
    void 댓글등록_3depth_초과시_예외() {
        // given
        Comment parent = createTestComment(1L, 1L, 1L);
        Comment child = createTestComment(2L, 1L, 1L);
        commentJpaRepository.save(parent);
        commentJpaRepository.save(child);

        CommentRegisterDto registerDto = new CommentRegisterDto();
        registerDto.setArticleId(1L);
        registerDto.setWriterId(102L);
        registerDto.setContent("3depth 댓글");
        registerDto.setParentCommentId(2L);

        // when & then
        assertThatThrownBy(() -> commentUpdateService.register(registerDto))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMMENT_MAX_DEPTH_EXCEED);
    }

    @Test
    void 댓글수정_성공() {
        // given
        Comment comment = createTestComment(1L, 1L, 1L);
        commentJpaRepository.save(comment);

        CommentUpdateDto updateDto = new CommentUpdateDto();
        updateDto.setContent("수정된 댓글");

        // when
        CommentDetailDto result = commentUpdateService.update(1L, updateDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("수정된 댓글");
        assertThat(result.getCommentId()).isEqualTo(1L);
    }

    @Test
    void 댓글수정_존재하지않는댓글_예외() {
        // given
        CommentUpdateDto updateDto = new CommentUpdateDto();
        updateDto.setContent("수정된 댓글");

        // when & then
        assertThatThrownBy(() -> commentUpdateService.update(999L, updateDto))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMMENT_NOT_FOUND);
    }

    @Test
    void 댓글삭제_자식댓글() {
        // given
        Comment parent = createTestComment(1L, 1L, 1L);
        Comment child = createTestComment(2L, 1L, 1L);
        commentJpaRepository.save(parent);
        commentJpaRepository.save(child);

        // when
        commentUpdateService.delete(2L);

        // then
        Optional<Comment> deleted = commentJpaRepository.findById(2L);
        assertThat(deleted).isEmpty();
    }

    @Test
    void 댓글삭제_부모댓글_자식있을때_소프트삭제() {
        // given
        Comment parent = createTestComment(1L, 1L, 1L);
        Comment child = createTestComment(2L, 1L, 1L);
        commentJpaRepository.save(parent);
        commentJpaRepository.save(child);

        // when
        commentUpdateService.delete(1L);

        // then
        Optional<Comment> deleted = commentJpaRepository.findById(1L);
        assertThat(deleted).isPresent();
        assertThat(deleted.get().getIsDeleted()).isTrue();
    }

    @Test
    void 댓글삭제_부모댓글_자식없을때_하드삭제() {
        // given
        Comment parent = createTestComment(1L, 1L, 1L);
        commentJpaRepository.save(parent);

        // when
        commentUpdateService.delete(1L);

        // then
        Optional<Comment> deleted = commentJpaRepository.findById(1L);
        assertThat(deleted).isEmpty();
    }
}
