package jhkim593.springboard.article.domain.event;

import jhkim593.springboard.article.domain.Article;

public record ArticleCreatedEvent (
       Article article
){ }
