package jhkim593.springboard.comment.common;

import jhkim593.springboard.comment.domain.model.Comment;

import java.time.LocalDateTime;

public class CommentDataFactory {

    private static final Long DEFAULT_COMMENT_ID = 1L;
    private static final String DEFAULT_CONTENT = "test comment content";
    private static final Long DEFAULT_ARTICLE_ID = 1L;
    private static final Long DEFAULT_WRITER_ID = 100L;
    private static final Boolean DEFAULT_IS_DELETED = false;

    private static Comment.CommentBuilder defaultCommentBuilder() {
        return Comment.builder()
                .commentId(DEFAULT_COMMENT_ID)
                .content(DEFAULT_CONTENT)
                .articleId(DEFAULT_ARTICLE_ID)
                .writerId(DEFAULT_WRITER_ID)
                .parentCommentId(null)
                .isDeleted(DEFAULT_IS_DELETED)
                .createdAt(LocalDateTime.now());
    }

//    public static Comment createTestComment() {
//        return defaultCommentBuilder().build();
//    }

//    public static Comment createTestComment(Long commentId) {
//        return defaultCommentBuilder()
//                .commentId(commentId)
//                .build();
//    }

//    public static Comment createTestComment(Long commentId, Long articleId) {
//        return defaultCommentBuilder()
//                .commentId(commentId)
//                .articleId(articleId)
//                .build();
//    }

    public static Comment createTestComment(Long commentId, Long articleId, Long parentCommentId) {
        return defaultCommentBuilder()
                .commentId(commentId)
                .articleId(articleId)
                .parentCommentId(parentCommentId)
                .build();
    }

    public static Comment createTestComment(Long commentId, Long articleId, Long writerId, String content, Long parentCommentId) {
        return defaultCommentBuilder()
                .commentId(commentId)
                .articleId(articleId)
                .writerId(writerId)
                .content(content)
                .parentCommentId(parentCommentId)
                .build();
    }

    public static Comment createTestComment(Long commentId, Long articleId, Long writerId, Long parentCommentId, String content) {
        return defaultCommentBuilder()
                .commentId(commentId)
                .articleId(articleId)
                .writerId(writerId)
                .parentCommentId(parentCommentId)
                .content(content)
                .build();
    }
}