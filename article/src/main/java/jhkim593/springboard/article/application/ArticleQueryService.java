package jhkim593.springboard.article.application;

import jhkim593.springboard.article.domain.dto.ArticlePageDto;
import jhkim593.springboard.article.application.provided.ArticleFinder;
import jhkim593.springboard.article.application.required.repository.ArticleQueryRepository;
import jhkim593.springboard.article.application.required.repository.ArticleRepository;
import jhkim593.springboard.article.domain.Article;
import jhkim593.springboard.article.domain.PageLimitCalculator;
import jhkim593.springboard.article.domain.dto.ArticleSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleQueryService implements ArticleFinder {
    private final ArticleQueryRepository queryRepository;
    private final ArticleRepository repository;

    @Override
    public Article findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public ArticlePageDto findArticlePage(Long boardId, Long pageNo, Long pageSize) {
        List<ArticleSummaryDto> articles = queryRepository.findArticlePage(boardId, pageNo, pageSize);
        return ArticlePageDto.create(articles,  queryRepository.countArticlePage(boardId, PageLimitCalculator.calculatePageLimit(pageNo,pageSize)));
    }
}
