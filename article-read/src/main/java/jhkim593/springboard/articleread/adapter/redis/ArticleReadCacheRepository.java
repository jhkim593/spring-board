package jhkim593.springboard.articleread.adapter.redis;

import jhkim593.springboard.articleread.application.required.repository.ArticleReadRepository;
import jhkim593.springboard.articleread.domain.ArticleRead;
import jhkim593.springboard.common.client.article.ArticleClient;
import jhkim593.springboard.common.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.event.DataSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ArticleReadCacheRepository implements ArticleReadRepository {
    
    private final StringRedisTemplate redisTemplate;
    private final ArticleClient articleClient;

    // article-read::article::{articleId}
    private static final String KEY_FORMAT = "article-read::article::%s";

    @Override
    public ArticleRead create(ArticleRead articleRead) {
        redisTemplate.opsForValue()
                .set(generateKey(articleRead.getArticleId()), DataSerializer.serialize(articleRead), Duration.ofDays(1));
        return articleRead;
    }

    @Override
    public ArticleRead update(ArticleRead articleRead) {
        redisTemplate.opsForValue().setIfPresent(generateKey(articleRead.getArticleId()), DataSerializer.serialize(articleRead));
        return articleRead;
    }

    @Override
    public void delete(Long articleId) {
        redisTemplate.delete(generateKey(articleId));
    }

    @Override
    public ArticleRead read(Long articleId) {
        String value = redisTemplate.opsForValue().get(generateKey(articleId));
        if(value == null){
            ArticleDetailDto articleDetail = articleClient.getArticle(articleId);
            ArticleRead articleRead = ArticleRead.create(articleDetail, 0L, 0L);
            createNotException(articleRead);
            return articleRead;
        }
        return DataSerializer.deserialize(value, ArticleRead.class);
    }

    @Override
    public Map<Long, ArticleRead> readAll(List<Long> articleIds) {
        List<String> keyList = articleIds.stream().map(this::generateKey).toList();
        List<String> values = redisTemplate.opsForValue().multiGet(keyList);
        Map<Long, ArticleRead> ArticleIdMap = new HashMap<>();

        for (int i = 0; i < articleIds.size(); i++) {
            Long articleId = articleIds.get(i);
            String value = values.get(i);

            if (value != null) {
                ArticleIdMap.put(articleId, DataSerializer.deserialize(value, ArticleRead.class));
                continue;
            }

            ArticleRead articleRead = read(articleId);
            if (articleRead != null) {
                ArticleIdMap.put(articleId, articleRead);
            }
        }
        return ArticleIdMap;

    }
    public ArticleRead createNotException(ArticleRead articleRead) {
        try {
            return create(articleRead);
        } catch (Exception e){
            log.error("ArticleReadCacheRepository.createNotException cache save fail error", e);
        }
        return null;
    }
    private String generateKey(Long articleId) {
        return KEY_FORMAT.formatted(articleId);
    }
}
