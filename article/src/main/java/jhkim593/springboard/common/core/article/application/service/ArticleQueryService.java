package jhkim593.springboard.common.core.article.application.service;

import jhkim593.springboard.common.core.article.application.provided.ArticleFinder;
import jhkim593.springboard.common.core.article.application.provided.BoardArticleCountFinder;
import jhkim593.springboard.common.core.article.application.required.repository.ArticleRepository;
import jhkim593.springboard.common.core.common.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.core.common.dto.article.ArticlePageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleQueryService implements ArticleFinder {
    private final ArticleRepository articleRepository;
    private final BoardArticleCountFinder boardArticleCountFinder;

    @Override
    public ArticleDetailDto findById(Long id) {
        return articleRepository.findById(id).createDetailDto();
    }

    @Override
    public ArticlePageDto findArticlePage(Long boardId, Long pageNo, Long pageSize) {
        List<ArticleDetailDto> articles = articleRepository.find(boardId, ( pageNo -1 ) * pageSize, pageSize);
        return ArticlePageDto.create(articles, boardArticleCountFinder.count(boardId));
    }
}
