package jhkim593.springboard.article.adapter.api;

import jhkim593.springboard.article.application.provided.ArticleFinder;
import jhkim593.springboard.article.application.provided.ArticleUpdater;
import jhkim593.springboard.article.application.provided.BoardArticleCountFinder;
import jhkim593.springboard.article.domain.Article;
import jhkim593.springboard.article.domain.dto.ArticleRegisterDto;
import jhkim593.springboard.article.domain.dto.ArticleUpdateDto;
import jhkim593.springboard.common.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.dto.article.ArticlePageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    
    private final ArticleFinder articleFinder;
    private final ArticleUpdater articleUpdater;
    private final BoardArticleCountFinder boardArticleCountFinder;

    @GetMapping("/api/v1/articles/{id}")
    public ResponseEntity<ArticleDetailDto> getArticle(@PathVariable Long id) {
        Article article = articleFinder.findById(id);
        return ResponseEntity.ok(article.createDetailDto());
    }

    @GetMapping("/api/v1/articles")
    public ResponseEntity<ArticlePageDto> getArticlePage(
            @RequestParam Long boardId,
            @RequestParam Long pageNo,
            @RequestParam Long pageSize) {
        return ResponseEntity.ok(articleFinder.findArticlePage(boardId, pageNo, pageSize));
    }

    @GetMapping("/api/v1/articles/boards/count/{boardId}")
    public ResponseEntity<Long> count(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardArticleCountFinder.count(boardId));
    }

    @PostMapping("/api/v1/articles")
    public ResponseEntity<ArticleDetailDto> createArticle(@RequestBody ArticleRegisterDto request) {
        Article article = articleUpdater.register(request);
        return ResponseEntity.ok(article.createDetailDto());
    }

    @PutMapping("/api/v1/articles/{id}")
    public ResponseEntity<ArticleDetailDto> updateArticle(
            @PathVariable Long id,
            @RequestBody ArticleUpdateDto request) {
        Article article = articleUpdater.update(id, request);
        return ResponseEntity.ok(article.createDetailDto());
    }

    @DeleteMapping("/api/v1/articles/{id}")
    public ResponseEntity<ArticleDetailDto> deleteArticle(@PathVariable Long id) {
        Article article = articleUpdater.delete(id);
        return ResponseEntity.ok(article.createDetailDto());
    }
}