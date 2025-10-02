package jhkim593.springboard.comment.domain.error;

import jhkim593.springboard.common.core.error.BaseErrorCode;
import lombok.Getter;

@Getter
public enum ErrorCode implements BaseErrorCode {
    COMMENT_NOT_FOUND("C001", 404,"Comment not found"),
    COMMENT_MAX_DEPTH_EXCEED("C002", 400,"Comment max depth exceed");

    private String code;
    private int status;
    private String message;

    ErrorCode(String code, int status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
