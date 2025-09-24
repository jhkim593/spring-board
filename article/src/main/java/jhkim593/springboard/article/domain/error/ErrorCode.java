package jhkim593.springboard.article.domain.error;

import jhkim593.springboard.common.core.error.BaseErrorCode;
import lombok.Getter;

@Getter
public enum ErrorCode implements BaseErrorCode {
    ARTICLE_NOT_FOUNT("A001", 404,"Article not found");

    private String code;
    private int status;
    private String message;

    ErrorCode(String code, int status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
