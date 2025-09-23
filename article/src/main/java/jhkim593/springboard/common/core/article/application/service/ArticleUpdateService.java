package jhkim593.springboard.common.core.article.application.service;

import jhkim593.springboard.common.core.article.application.provided.ArticleUpdater;
import jhkim593.springboard.common.core.article.application.provided.BoardArticleCountUpdater;
import jhkim593.springboard.common.core.article.application.required.event.EventPublisher;
import jhkim593.springboard.common.core.article.application.required.repository.ArticleRepository;
import jhkim593.springboard.common.core.article.domain.dto.ArticleRegisterDto;
import jhkim593.springboard.common.core.article.domain.dto.ArticleUpdateDto;
import jhkim593.springboard.common.core.article.domain.model.Article;
import jhkim593.springboard.common.core.common.snowflake.DBIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleUpdateService implements ArticleUpdater {
    private final ArticleRepository articleRepository;
    private final BoardArticleCountUpdater boardArticleCountUpdater;
    private final DBIdGenerator idGenerator;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public Article register(ArticleRegisterDto request) {
        Article article = articleRepository.save(Article.create(idGenerator.getId(), request));

        Long count = boardArticleCountUpdater.increase(article.getBoardId());

        eventPublisher.registeredEventPublish(article.createRegisteredEventPayload(count));
        return article;
    }

    @Override
    @Transactional
    public Article update(Long id, ArticleUpdateDto request) {
         Article article = articleRepository.findById(id);
         article.update(request);
         article = articleRepository.save(article);

         eventPublisher.updatedEventPublish(article.createUpdatedEventPayload());
         return article;
    }

    @Override
    @Transactional
    public Article delete(Long id) {
        Article article = articleRepository.findById(id);
        article.delete();
        articleRepository.save(article);

        Long count = boardArticleCountUpdater.decrease(article.getBoardId());

        eventPublisher.deletedEventPublish(article.createDeletedEventPayload(count));

        return article;
    }
}
