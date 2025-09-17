//package jhkim593.springboard.article.fake;
//
//import jhkim593.springboard.article.application.required.repository.ArticleRepository;
//import jhkim593.springboard.article.domain.model.Article;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//public class FakeArticleRepository implements ArticleRepository {
//
//    private final Map<Long, Article> articles = new HashMap<>();
//    private Long nextId = 1L;
//
//    public void addArticle(Article article) {
//        articles.put(article.getArticleId(), article);
//    }
//
//    public void clear() {
//        articles.clear();
//        nextId = 1L;
//    }
//
//    @Override
//    public Optional<Article> findById(Long id) {
//        return Optional.ofNullable(articles.get(id));
//    }
//
//    @Override
//    public Article save(Article article) {
//        if (article.getArticleId() == null) {
//            Article savedArticle = Article.builder()
//                    .articleId(nextId++)
//                    .title(article.getTitle())
//                    .content(article.getContent())
//                    .boardId(article.getBoardId())
//                    .writerId(article.getWriterId())
//                    .deleted(article.getDeleted())
//                    .createdAt(article.getCreatedAt())
//                    .modifiedAt(article.getModifiedAt())
//                    .build();
//            articles.put(savedArticle.getArticleId(), savedArticle);
//            return savedArticle;
//        } else {
//            articles.put(article.getArticleId(), article);
//            return article;
//        }
//    }
//    @Override
//    public void deleteById(Long id) {
//        articles.remove(id);
//    }
//}