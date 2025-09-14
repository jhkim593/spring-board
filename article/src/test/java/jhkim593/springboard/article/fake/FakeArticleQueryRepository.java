package jhkim593.springboard.article.fake;

import jhkim593.springboard.article.application.required.repository.ArticleQueryRepository;
import jhkim593.springboard.common.dto.article.ArticleDetailDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeArticleQueryRepository implements ArticleQueryRepository {
    private final Map<Long, List<ArticleDetailDto>> boardArticles = new HashMap<>();

    @Override
    public List<ArticleDetailDto> findArticlePage(Long boardId, Long pageNo, Long pageSize) {
        List<ArticleDetailDto> articles = boardArticles.getOrDefault(boardId, new ArrayList<>());

        int startIndex = (int) ((pageNo - 1) * pageSize);
        int endIndex = Math.min(startIndex + pageSize.intValue(), articles.size());

        if (startIndex >= articles.size()) {
            return new ArrayList<>();
        }

        return articles.subList(startIndex, endIndex);
    }

    public void addArticle(Long boardId, ArticleDetailDto article) {
        boardArticles.computeIfAbsent(boardId, k -> new ArrayList<>()).add(article);
    }
    
    public void addArticles(Long boardId, List<ArticleDetailDto> articles) {
        boardArticles.computeIfAbsent(boardId, k -> new ArrayList<>()).addAll(articles);
    }
    
    public void clear() {
        boardArticles.clear();
    }
    
    public static ArticleDetailDto createTestArticleDetailDto(Long articleId, Long boardId) {
        return ArticleDetailDto.builder()
                .articleId(articleId)
                .title("test title " + articleId)
                .content("test content " + articleId)
                .boardId(boardId)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}