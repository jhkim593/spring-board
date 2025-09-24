package jhkim593.springboard.articleread.adapter.api;


import jhkim593.springboard.articleread.application.provided.ArticleReadFinder;
import jhkim593.springboard.articleread.domain.dto.ArticleReadDetailDto;
import jhkim593.springboard.articleread.domain.dto.ArticleReadPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleReadController {
    private final ArticleReadFinder articleReadFinder;

    @GetMapping("/api/v1/articles/{id}")
    public ResponseEntity<ArticleReadDetailDto> read(@PathVariable("id") Long articleId) {
        return ResponseEntity.ok(articleReadFinder.read(articleId));
    }

    @GetMapping("/api/v1/articles")
    public ResponseEntity<ArticleReadPageDto> readAll(
            @RequestParam("boardId") Long boardId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ) {
        return ResponseEntity.ok(articleReadFinder.readPage(boardId, page, pageSize));
    }
}

