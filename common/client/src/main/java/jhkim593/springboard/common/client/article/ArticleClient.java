package jhkim593.springboard.common.client.article;

import feign.Param;
import feign.RequestLine;
import jhkim593.springboard.common.core.common.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.core.common.dto.article.ArticlePageDto;

public interface ArticleClient {

    @RequestLine("GET /api/v1/articles/{id}")
    ArticleDetailDto getArticle(@Param Long id);

    @RequestLine("GET /api/v1/articles/boards/count/{boardId}")
    Long getArticleCount(@Param Long boardId);

    @RequestLine("GET /api/v1/articles?boardId={boardId}&pageNo={pageNo}&pageSize={pageSize}")
    ArticlePageDto getArticlePage(@Param Long boardId, @Param Long pageNo, @Param Long pageSize);
}
