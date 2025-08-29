package jhkim593.springboard.article.domain;

public final class PageLimitCalculator {
    public static Long calculatePageLimit(Long page, Long pageSize) {
        long movablePageCount = 10L;
        return (((page - 1) / movablePageCount) + 1) * pageSize * movablePageCount + 1;
    }
}
