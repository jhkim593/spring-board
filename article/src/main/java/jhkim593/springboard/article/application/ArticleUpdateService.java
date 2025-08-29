package jhkim593.springboard.article.application;

import jhkim593.springboard.article.application.provided.ArticleFinder;
import jhkim593.springboard.article.application.provided.ArticleUpdater;
import jhkim593.springboard.article.application.required.event.EventPublisher;
import jhkim593.springboard.article.application.required.repository.ArticleRepository;
import jhkim593.springboard.article.domain.Article;
import jhkim593.springboard.article.domain.dto.ArticleRegisterDto;
import jhkim593.springboard.article.domain.dto.ArticleUpdateDto;
import jhkim593.springboard.article.domain.event.ArticleCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleUpdateService implements ArticleUpdater {
    private final ArticleFinder articleFinder;
    private final ArticleRepository articleRepository;
    private final EventPublisher eventPublisher;

    @Override
    public Article register(ArticleRegisterDto request) {
        Article article = articleRepository.save(Article.create(request));
        eventPublisher.publish(new ArticleCreatedEvent(article));
        return article;
    }

    @Override
    public Article update(Long id, ArticleUpdateDto request) {
         Article article = articleFinder.findById(id);
         article.update(request);
        return articleRepository.save(article);
    }

    @Override
    public Article delete(Long id) {
        Article article = articleFinder.findById(id);
        article.delete();
        return article;
    }
}
