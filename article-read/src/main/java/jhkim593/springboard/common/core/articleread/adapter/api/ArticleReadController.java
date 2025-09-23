package jhkim593.springboard.common.core.articleread.adapter.api;


import jhkim593.springboard.common.core.articleread.application.provided.ArticleReadFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

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

