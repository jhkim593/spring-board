package jhkim593.springboard.article.application;

import jhkim593.springboard.article.application.provided.ArticleFinder;
import jhkim593.springboard.article.application.provided.BoardArticleCountFinder;
import jhkim593.springboard.article.application.required.repository.ArticleQueryRepository;
import jhkim593.springboard.article.application.required.repository.ArticleRepository;
import jhkim593.springboard.article.domain.Article;
import jhkim593.springboard.article.domain.error.ErrorCode;
import jhkim593.springboard.common.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.dto.article.ArticlePageDto;
import jhkim593.springboard.common.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleQueryService implements ArticleFinder {
    private final ArticleQueryRepository queryRepository;
    private final ArticleRepository repository;
    private final BoardArticleCountFinder boardArticleCountFinder;

    @Override
    public Article findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUNT));
    }

    @Override
    public ArticlePageDto findArticlePage(Long boardId, Long pageNo, Long pageSize) {
        List<ArticleDetailDto> articles = queryRepository.findArticlePage(boardId, pageNo, pageSize);
        return ArticlePageDto.create(articles, boardArticleCountFinder.count(boardId));
    }
}
