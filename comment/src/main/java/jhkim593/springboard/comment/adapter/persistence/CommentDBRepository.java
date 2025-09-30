package jhkim593.springboard.comment.adapter.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
        Comment result = jpaQueryFactory
                .selectFrom(qComment)
                .join(qComment.parent).fetchJoin()
                .where(qComment.commentId.eq(id))
                .fetchOne();

        if (result == null) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }
        return result;
    }
    @Override
    public void delete(Long id) {
        commentJpaRepository.deleteById(id);
    }

    @Override
    public boolean hasChildComment(Long articleId, Long parentCommentId) {
        return jpaQueryFactory
                .selectOne()
                .from(qComment)
                .where(
                    articleIdEq(articleId)
                    ,parentCommentIdEq(parentCommentId))
                .fetchFirst() != null;
    }

    @Override
    public List<CommentDetailDto> find(Long articleId, Long lastParentCommentId, Long lastCommentId) {
        QComment parent = new QComment("parent");
        if (lastParentCommentId == null) {
            lastParentCommentId = lastCommentId;
        }

        return jpaQueryFactory
                .select(
                        Projections.constructor(CommentDetailDto.class,
                                qComment.commentId,
                                qComment.content,
                                qComment.articleId,
                                parent.commentId,
                                qComment.writerId,
                                qComment.createdAt
                        )
                )
                .from(qComment)
                .join(qComment.parent, parent)
                .where(
                        articleIdEq(articleId),
                        (
                                parent.isNull().and(lastParentCommentIdGt(lastParentCommentId, parent))
                                .or
                                (parent.isNotNull().and(parent.commentId.eq(lastParentCommentId)).and(lastCommentIdGt(lastCommentId)))
                        )
                )
                .orderBy(parent.commentId.asc(), qComment.commentId.asc())
                .limit(30)
                .fetch();
    }

    private BooleanExpression articleIdEq(Long articleId) {
        return articleId == null ? null: qComment.articleId.eq(articleId);
    }
    private BooleanExpression lastCommentIdGt(Long lastCommentId) {
        return lastCommentId == null ? null : qComment.commentId.gt(lastCommentId);
    }
    private BooleanExpression lastParentCommentIdGt(Long lastCommentId, QComment parent) {
        return lastCommentId == null ? null : parent.commentId.gt(lastCommentId);
    }

    private BooleanExpression isNotDeleted() {
        return qComment.isDeleted.eq(false);
    }
}
