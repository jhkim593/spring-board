package jhkim593.springboard.article.adapter.api;

import jhkim593.springboard.article.application.provided.ArticleFinder;
import jhkim593.springboard.article.application.provided.ArticleUpdater;
import jhkim593.springboard.article.domain.Article;
import jhkim593.springboard.article.domain.dto.ArticleRegisterDto;
import jhkim593.springboard.article.domain.dto.ArticleUpdateDto;
import jhkim593.springboard.article.domain.dto.ArticleDetailDto;
import jhkim593.springboard.article.domain.dto.ArticlePageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    
    private final ArticleFinder articleFinder;
    private final ArticleUpdater articleUpdater;

    @GetMapping("/v1/{id}")
    public ResponseEntity<ArticleDetailDto> getArticle(@PathVariable Long id) {
        Article article = articleFinder.findById(id);
        return ResponseEntity.ok(ArticleDetailDto.create(article));
    }

    @GetMapping
    public ResponseEntity<ArticlePageDto> getArticlePage(
            @RequestParam Long boardId,
            @RequestParam Long pageNo,
            @RequestParam Long pageSize) {
        
        return ResponseEntity.ok(articleFinder.findArticlePage(boardId, pageNo, pageSize));
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody ArticleRegisterDto request) {
        Article article = articleUpdater.register(request);
        return ResponseEntity.ok(article);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(
            @PathVariable Long id,
            @RequestBody ArticleUpdateDto request) {
        Article article = articleUpdater.update(id, request);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable Long id) {
        Article article = articleUpdater.delete(id);
        return ResponseEntity.ok(article);
    }
}