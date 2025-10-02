package jhkim593.springboard.comment.adapter.persistence;

import jhkim593.springboard.comment.adapter.persistence.jpa.CommentJpaRepository;
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

import java.util.List;

import static jhkim593.springboard.comment.common.CommentDataFactory.createTestComment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(TestConfig.class)
class CommentDBRepositoryTest {

    @Autowired
    private CommentDBRepository commentDBRepository;

    @Autowired
    private CommentJpaRepository commentJpaRepository;

    @Autowired
    private DBCleanManager dbCleanManager;

    @AfterEach
    public void clear(){
        dbCleanManager.execute();
    }

    @Test
    void 댓글저장() {
        // given
        Comment comment = createTestComment(1L, 1L, 100L, "댓글 내용",2L);

        // when
        Comment savedComment = commentDBRepository.save(comment);

        // then
        assertThat(savedComment.getCommentId()).isEqualTo(1L);
        assertThat(savedComment.getContent()).isEqualTo("댓글 내용");
        assertThat(savedComment.getArticleId()).isEqualTo(1L);
        assertThat(savedComment.getWriterId()).isEqualTo(100L);
        assertThat(savedComment.getParentCommentId()).isEqualTo(2L);
    }

    @Test
    void 댓글ID조회_성공() {
        // given
        commentJpaRepository.save(createTestComment(1L, 1L,1L));

        // when
        Comment foundComment = commentDBRepository.findById(1L);

        // then
        assertThat(foundComment.getCommentId()).isEqualTo(1L);
        assertThat(foundComment.getArticleId()).isEqualTo(1L);
    }

    @Test
    void 댓글ID조회_존재하지않을때예외() {
        // when & then
        assertThatThrownBy(() -> commentDBRepository.findById(999L))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMMENT_NOT_FOUND);
    }

    @Test
    void 댓글삭제_성공() {
        // given
        commentDBRepository.save(createTestComment(1L, 1L,1L));

        // when
        commentDBRepository.delete(1L);

        // then
        assertThatThrownBy(() -> commentDBRepository.findById(1L))
                .isInstanceOf(CustomException.class);
    }

    @Test
    void 자식댓글존재확인() {
        // given
        Long articleId = 1L;
        commentDBRepository.save(createTestComment(1L, articleId,1L));
        commentDBRepository.save(createTestComment(2L, articleId, 1L));

        // when
        boolean hasChild = commentDBRepository.hasChildComment(articleId, 1L);

        // then
        assertThat(hasChild).isTrue();
    }

    @Test
    void 자식댓글존재확인_존재하지않음() {
        // given
        Long articleId = 1L;
        commentDBRepository.save(createTestComment(1L, articleId,1L));

        // when
        boolean hasChild = commentDBRepository.hasChildComment(articleId, 1L);

        // then
        assertThat(hasChild).isFalse();
    }

    @Test
    void 댓글목록조회_첫페이지() {
        // given
        Long articleId = 1L;
        // 부모 댓글1과 자식들
        commentDBRepository.save(createTestComment(1L, articleId,1L));
        commentDBRepository.save(createTestComment(2L, articleId, 1L));
        commentDBRepository.save(createTestComment(3L, articleId, 1L));

        // 부모 댓글2와 자식들
        commentDBRepository.save(createTestComment(4L, articleId,4L));
        commentDBRepository.save(createTestComment(5L, articleId, 4L));

        // when
        List<CommentDetailDto> result = commentDBRepository.find(articleId, null, null,4);

        // then
        assertThat(result).hasSize(4);
        assertThat(result.get(0).getCommentId()).isEqualTo(1L);
        assertThat(result.get(1).getCommentId()).isEqualTo(2L);
        assertThat(result.get(2).getCommentId()).isEqualTo(3L);
        assertThat(result.get(3).getCommentId()).isEqualTo(4L);
    }

    @Test
    void 댓글목록조회_무한스크롤() {
        // given
        Long articleId = 1L;

        // 부모 댓글1과 자식들
        commentDBRepository.save(createTestComment(1L, articleId,1L));
        commentDBRepository.save(createTestComment(2L, articleId, 1L));
        commentDBRepository.save(createTestComment(3L, articleId, 1L));

        // 부모 댓글2와 자식들
        commentDBRepository.save(createTestComment(4L, articleId,4L));
        commentDBRepository.save(createTestComment(5L, articleId, 4L));

        // when - 마지막 댓글이 parentCommentId=1, commentId=2인 경우
        List<CommentDetailDto> result = commentDBRepository.find(articleId, 1L, 2L, 30);

        // then - (parentCommentId=1 AND commentId>2) OR (parentCommentId>1) 조건
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getCommentId()).isEqualTo(3L);
        assertThat(result.get(1).getCommentId()).isEqualTo(4L);
        assertThat(result.get(2).getCommentId()).isEqualTo(5L);
    }

    @Test
    void 댓글목록조회_부모ID우선정렬확인() {
        // given
        Long articleId = 1L;

        // 부모 댓글들
        commentDBRepository.save(createTestComment(1L, articleId,1L));
        commentDBRepository.save(createTestComment(2L, articleId,2L));

        // 부모 댓글1의 자식들
        commentDBRepository.save(createTestComment(3L, articleId, 1L));
        commentDBRepository.save(createTestComment(4L, articleId, 2L));

        // when
        List<CommentDetailDto> result = commentDBRepository.find(articleId, null, null,30);

        // then
        assertThat(result).hasSize(4);
        // parentCommentId 오름차순, commentId 오름차순으로 정렬
        assertThat(result.get(0).getCommentId()).isEqualTo(1L); // 부모 댓글1
        assertThat(result.get(1).getCommentId()).isEqualTo(3L); // 자식 댓글1-1
        assertThat(result.get(2).getCommentId()).isEqualTo(2L); // 보모 댓글2
        assertThat(result.get(3).getCommentId()).isEqualTo(4L); // 자식 댓글2-1
    }

    @Test
    void 댓글목록조회_결과없음() {
        // when
        List<CommentDetailDto> result = commentDBRepository.find(999L, null, null,30);

        // then
        assertThat(result).isEmpty();
    }
}