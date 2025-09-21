package jhkim593.springboard.article.adapter.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jhkim593.springboard.article.adapter.persistence.jpa.ArticleJpaRepository;
import jhkim593.springboard.article.application.required.repository.ArticleRepository;
import jhkim593.springboard.article.domain.error.ErrorCode;
import jhkim593.springboard.article.domain.model.Article;
import jhkim593.springboard.article.domain.model.QArticle;
import jhkim593.springboard.common.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleDBRepository implements ArticleRepository {
    private final ArticleJpaRepository articleJpaRepository;
    private final JPAQueryFactory queryFactory;
    private QArticle qArticle = QArticle.article;

    @Override
    public Article findById(Long id) {
        return articleJpaRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUNT));
    }

    @Override
    public Article save(Article article) {
        return articleJpaRepository.save(article);
    }

    @Override
    public List<ArticleDetailDto> find(Long boardId, Long offset, Long limit) {
        List<Long> articleIds = findArticleIds(boardId, offset, limit);

        if (articleIds.isEmpty()) {
            return List.of();
        }
        return queryFactory
                .select(
                        Projections.constructor(ArticleDetailDto.class,
                                qArticle.articleId,
                                qArticle.title,
                                qArticle.content,
                                qArticle.boardId,
                                qArticle.writerId,
                                qArticle.createdAt,
                                qArticle.modifiedAt
                        )
                )
                .from(qArticle)
                .where(qArticle.articleId.in(articleIds))
                .orderBy(qArticle.articleId.desc())
                .fetch();
    }

    private List<Long> findArticleIds(Long boardId, Long pageNo, Long pageSize) {
        return queryFactory
                .select(qArticle.articleId)
                .from(qArticle)
                .where(
                        qArticle.deleted.isFalse()
                                .and(qArticle.boardId.eq(boardId))
                )
                .orderBy(qArticle.articleId.desc())
                .offset(pageNo * pageSize)
                .limit(pageSize)
                .fetch();
    }
}
