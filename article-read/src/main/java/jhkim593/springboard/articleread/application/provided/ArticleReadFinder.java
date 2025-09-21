package jhkim593.springboard.articleread.application.provided;

import jhkim593.springboard.articleread.domain.dto.ArticleReadDetailDto;
import jhkim593.springboard.articleread.domain.dto.ArticleReadPageDto;

public interface ArticleReadFinder {
    ArticleReadDetailDto read(Long articleId);

    ArticleReadPageDto readPage(Long boardId, Long page, Long pageSize);
}
