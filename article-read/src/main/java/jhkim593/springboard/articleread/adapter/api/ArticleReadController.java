package jhkim593.springboard.articleread.adapter.api;


import jhkim593.springboard.articleread.application.provided.ArticleReadFinder;
import jhkim593.springboard.articleread.application.provided.ArticleReadUpdater;
import jhkim593.springboard.articleread.domain.dto.ArticleReadDetailDto;
import jhkim593.springboard.articleread.domain.dto.ArticleReadPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleReadController {
    private final ArticleReadFinder articleReadFinder;

//    @GetMapping("/v1/articles/{articleId}")
//    public ArticleReadDetailDto read(@PathVariable("articleId") Long articleId) {
//        return articleReadFinder.read(articleId);
//    }
//
//    @GetMapping("/v1/articles")
//    public ArticleReadPageDto readAll(
//            @RequestParam("boardId") Long boardId,
//            @RequestParam("page") Long page,
//            @RequestParam("pageSize") Long pageSize
//    ) {
//        return articleReadFinder.readAll(boardId, page, pageSize);
//    }
//
//    @GetMapping("/v1/articles/infinite-scroll")
//    public List<ArticleReadDetailDto> readAllInfiniteScroll(
//            @RequestParam("boardId") Long boardId,
//            @RequestParam(value = "lastArticleId", required = false) Long lastArticleId,
//            @RequestParam("pageSize") Long pageSize
//    ) {
//        return articleReadFinder.readAllInfiniteScroll(boardId, lastArticleId, pageSize);
//    }

}

