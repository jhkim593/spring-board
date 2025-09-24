package jhkim593.springboard.articleread.adapter.redis;

import jhkim593.springboard.articleread.application.required.repository.BoardArticleCountRepository;
import jhkim593.springboard.common.client.article.ArticleClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BoardArticleCountCacheRepository implements BoardArticleCountRepository {
    private final StringRedisTemplate redisTemplate;
    private final ArticleClient articleClient;


    // article-read::board-article-count::board::{boardId}
    private static final String KEY_FORMAT = "article-read::board-article-count::board::%s";

    @Override
    public void update(Long boardId, Long articleCount) {
        redisTemplate.opsForValue().set(generateKey(boardId), String.valueOf(articleCount));
    }

    @Override
    public Long count(Long boardId) {
        String value = redisTemplate.opsForValue().get(generateKey(boardId));
        if(value != null) return Long.valueOf(value);

        Long count = getArticleCount(boardId);
        updateNotException(boardId, count);
        return count;
    }

    private Long getArticleCount(Long boardId) {
        try {
            return articleClient.getArticleCount(boardId);
        } catch (Exception e) {
            log.error("BoardArticleCountCacheRepository.getArticleCount articleClient.getArticleCount fail error", e);
            return 0L;
        }
    }

    @Override
    public String generateKey(Long boardId) {
        return KEY_FORMAT.formatted(boardId);
    }

    private void updateNotException(Long boardId, Long articleCount) {
        try {
            update(boardId, articleCount);
        } catch (Exception e) {
            log.error("BoardArticleCountCacheRepository.updateNotException cache save fail error", e);
        }
    }
}
