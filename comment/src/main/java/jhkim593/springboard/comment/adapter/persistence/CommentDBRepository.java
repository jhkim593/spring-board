package jhkim593.springboard.comment.adapter.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Coalesce;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jhkim593.springboard.comment.adapter.persistence.jpa.CommentJpaRepository;
import jhkim593.springboard.comment.application.required.CommentRepository;
import jhkim593.springboard.comment.domain.error.ErrorCode;
import jhkim593.springboard.comment.domain.model.Comment;
import jhkim593.springboard.comment.domain.model.QComment;
import jhkim593.springboard.common.core.dto.comment.CommentDetailDto;
import jhkim593.springboard.common.core.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDBRepository implements CommentRepository {
    private final CommentJpaRepository commentJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private QComment qComment = QComment.comment;

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(comment);
    }
    @Override
    public Comment findById(Long id) {
        return commentJpaRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }
    @Override
    public void delete(Long id) {
        commentJpaRepository.deleteById(id);
    }

    @Override
    public boolean hasChildComment(Long articleId, Long parentCommentId) {
        List<Long> commentIds = jpaQueryFactory
                .select(qComment.commentId)
                .from(qComment)
                .where(
                        articleIdEq(articleId)
                        ,parentCommentIdEq(parentCommentId))
                .limit(2)
                .fetch();
        return commentIds.size() == 2 ? true : false;
    }

    @Override
    public List<CommentDetailDto> find(Long articleId, Long lastParentCommentId, Long lastCommentId, int limit) {

        return jpaQueryFactory
                .select(
                        Projections.constructor(CommentDetailDto.class,
                                qComment.commentId,
                                qComment.content,
                                qComment.articleId,
                                qComment.parentCommentId,
                                qComment.writerId,
                                qComment.isDeleted,
                                qComment.createdAt
                        )
                )
                .from(qComment)
                .where(
                        articleIdEq(articleId),
                        lastParentCommentIdLastCommentIdCheck(lastParentCommentId, lastCommentId))
                .orderBy(
                        qComment.parentCommentId.asc(), qComment.commentId.asc()
                )
                .limit(limit)
                .fetch();
    }

    private BooleanExpression articleIdEq(Long articleId) {
        return articleId == null ? null: qComment.articleId.eq(articleId);
    }
    private BooleanExpression lastParentCommentIdLastCommentIdCheck(Long parentCommentId, Long lastCommentId) {
        if(parentCommentId == null || lastCommentId == null) {
            return null;
        }
        return parentCommentIdEq(parentCommentId).and(lastCommentIdGt(lastCommentId)).or(lastParentCommentIdGt(parentCommentId));
    }
    private BooleanExpression parentCommentIdEq(Long parentCommentId) {
        return parentCommentId == null ? null: qComment.parentCommentId.eq(parentCommentId);
    }
    private BooleanExpression lastCommentIdGt(Long lastCommentId) {
        return lastCommentId == null ? null : qComment.commentId.gt(lastCommentId);
    }
    private BooleanExpression lastParentCommentIdGt(Long lastParentCommentId) {
        return lastParentCommentId == null ? null : qComment.parentCommentId.gt(lastParentCommentId);
    }
}
