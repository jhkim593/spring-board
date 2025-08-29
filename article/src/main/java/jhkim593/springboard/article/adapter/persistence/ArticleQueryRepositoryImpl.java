package jhkim593.springboard.article.adapter.persistence;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jhkim593.springboard.article.application.required.repository.ArticleQueryRepository;
import jhkim593.springboard.article.domain.QArticle;
import jhkim593.springboard.article.domain.dto.ArticleSummaryDto;
import jhkim593.springboard.article.domain.response.QArticleSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleQueryRepositoryImpl implements ArticleQueryRepository {
    private final JPAQueryFactory queryFactory;
    private QArticle qArticle = QArticle.article;

    @Override
    public List<ArticleSummaryDto> findArticlePage(Long boardId, Long pageNo, Long pageSize) {
        return queryFactory
                .select(
                    new QArticleSummaryDto(
                            qArticle.articleId,
                            qArticle.title,
                            qArticle.content,
                            qArticle.boardId,
                            qArticle.writerId,
                            qArticle.createdAt
                    )
                )
                .from(qArticle)
                .where(qArticle.articleId.in(
                        JPAExpressions
                                .select(qArticle.articleId)
                                .from(qArticle)
                                .offset(pageNo * pageSize)
                                .limit(pageSize)
                                .where(
                                    qArticle.deleted.isFalse()
                                    .and(qArticle.boardId.eq(boardId))
                                )
                                .orderBy(qArticle.articleId.desc())
                ))
                .orderBy(qArticle.articleId.desc())
                .fetch();
    }

    @Override
    public Long countArticlePage(Long boardId, Long limit) {
        return queryFactory
                .select(qArticle.articleId.count())
                .from(qArticle)
                .where(qArticle.boardId.eq(boardId))
                .orderBy(qArticle.articleId.desc())
                .limit(limit)
                .fetchOne();
    }
}
