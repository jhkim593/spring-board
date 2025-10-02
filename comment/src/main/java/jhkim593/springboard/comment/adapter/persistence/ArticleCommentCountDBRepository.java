package jhkim593.springboard.comment.adapter.persistence;

import jhkim593.springboard.comment.adapter.persistence.jpa.ArticleCommentCountJpaRepository;
import jhkim593.springboard.comment.application.required.ArticleCommentCountRepository;
import jhkim593.springboard.comment.domain.model.ArticleCommentCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleCommentCountDBRepository implements ArticleCommentCountRepository {
    private final ArticleCommentCountJpaRepository articleCommentCountJpaRepository;

    @Override
    public ArticleCommentCount findById(Long id) {
        return articleCommentCountJpaRepository.findById(id).orElse(null);
    }

    @Override
    public ArticleCommentCount save(ArticleCommentCount articleCommentCount) {
        return articleCommentCountJpaRepository.save(articleCommentCount);
    }

    @Override
    public void delete(Long id) {
        articleCommentCountJpaRepository.deleteById(id);
    }
}
