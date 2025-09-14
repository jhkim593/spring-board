package jhkim593.springboard.articleread.application;

import jhkim593.springboard.articleread.application.provided.ArticleReadFinder;
import jhkim593.springboard.articleread.application.required.repository.ArticleIdRepository;
import jhkim593.springboard.articleread.application.required.repository.ArticleReadRepository;
import jhkim593.springboard.articleread.application.required.repository.BoardArticleCountRepository;
import jhkim593.springboard.articleread.domain.ArticleRead;
import jhkim593.springboard.articleread.domain.dto.ArticleReadDetailDto;
import jhkim593.springboard.articleread.domain.dto.ArticleReadPageDto;
import jhkim593.springboard.common.client.article.ArticleClient;
import jhkim593.springboard.common.dto.article.ArticleDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleReadQueryService implements ArticleReadFinder {
    private final ArticleReadRepository articleReadRepository;
    private final ArticleIdRepository articleIdRepository;
    private final BoardArticleCountRepository boardArticleCountRepository;
    private final ArticleClient articleClient;

//    //만약 redis에 없으면 조회하고 캐시에 저장
//    private ArticleRead fetch(Long articleId) {
//        try {
//            ArticleDetailDto articleDetail = articleClient.getArticle(articleId);
//            ArticleRead articleRead = ArticleRead.create(articleDetail, 0L, 0L);
//            //TODO 이거 다른 스레드에서 실행 해도 되는데 이벤트로 처리할지, 아니면 다른 방법은
//            articleReadRepository.create(articleRead);
//            return articleRead;
//        } catch (Exception e) {
//            log.error("[ArticleReadService.fetch] error", e);
//            return null;
//        }
//    }

    public ArticleReadPageDto readAll(Long boardId, Long page, Long pageSize) {
        List<Long> articleIds = articleIdRepository.readAll(boardId, page, pageSize);
        return ArticleReadPageDto.create(
                ArticleRead.createDetailDtos(readAll(articleIds)),
                count(boardId)
        );
    }

    private List<ArticleRead> readAll(List<Long> articleIds) {
        return new ArrayList<>(articleReadRepository.readAll(articleIds).values());
//        return articleIds.stream()
//                .map(articleId -> articleReadMap.containsKey(articleId) ?
//                        articleReadMap.get(articleId) :
//                        fetch(articleId))
//                .filter(Objects::nonNull)
//                .toList();
//                .map(articleRead ->
//                        ArticleRead(
//                                articleQueryModel,
//                                viewClient.count(articleQueryModel.getArticleId())
//                        ))
//                .toList();
    }

    //TODO 이거 클라이언트 요청하는건 각 레포에 책임일듯
    // 각 레포에 데이터 없으면 클라이언트 조회하는 로직 추가하자
    private List<Long> readAllArticleIds(Long boardId, Long page, Long pageSize) {
        List<Long> articleIds = articleIdRepository.readAll(boardId, (page - 1) * pageSize, pageSize);
        if (pageSize == articleIds.size()) {
            log.info("[ArticleReadService.readAllArticleIds] return redis data.");
            return articleIds;
        }
        log.info("[ArticleReadService.readAllArticleIds] return origin data.");
        return articleClient.getArticlePage(boardId, page, pageSize).getData().stream()
                .map(ArticleDetailDto::getArticleId)
                .toList();
    }

    private long count(Long boardId) {
        Long count = boardArticleCountRepository.count(boardId);
        if (count != null) {
            return count;
        }
        count = articleClient.getArticleCount(boardId);
        boardArticleCountRepository.update(boardId, count);
        return count;
    }

//    public List<ArticleRead> readAllInfiniteScroll(Long boardId, Long lastArticleId, Long pageSize) {
//        List<Long> articleIds = articleIdRepository.readAllInfiniteScroll(boardId, lastArticleId, pageSize);
//        Map<Long, ArticleRead> articles = articleReadRepository.readAll(articleIds);
//        return new ArrayList<>(articles.values());
//        return readAll(
//                readAllInfiniteScrollArticleIds(boardId, lastArticleId, pageSize)
//        );
//    }
//
//    private List<Long> readAllInfiniteScrollArticleIds(Long boardId, Long lastArticleId, Long pageSize) {
//        List<Long> articleIds = articleIdRepository.readAllInfiniteScroll(boardId, lastArticleId, pageSize);
//        if (pageSize == articleIds.size()) {
//            log.info("[ArticleReadService.readAllInfiniteScrollArticleIds] return redis data.");
//            return articleIds;
//        }
//        log.info("[ArticleReadService.readAllInfiniteScrollArticleIds] return origin data.");
//        return articleClient.(boardId, lastArticleId, pageSize).stream()
//                .map(ArticleClient.ArticleResponse::getArticleId)
//                .toList();
//    }
}

