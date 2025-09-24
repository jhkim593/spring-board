package jhkim593.springboard.articleread.adapter.redis;

import jhkim593.springboard.articleread.application.required.repository.ArticleReadRepository;
import jhkim593.springboard.articleread.domain.ArticleRead;
import jhkim593.springboard.articleread.domain.error.ErrorCode;
import jhkim593.springboard.common.client.article.ArticleClient;
import jhkim593.springboard.common.core.dto.article.ArticleDetailDto;
import jhkim593.springboard.common.core.error.CustomException;
import jhkim593.springboard.common.core.util.DataSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

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
        if (value == null) {
            ArticleDetailDto articleDetail = getArticleDetailDto(articleId);
            ArticleRead articleRead = ArticleRead.create(articleDetail, 0L, 0L);
            createNotException(articleRead);
            return articleRead;
        }
        return DataSerializer.deserialize(value, ArticleRead.class);
    }

    private ArticleDetailDto getArticleDetailDto(Long articleId) {
        try {
            ArticleDetailDto articleDetail = articleClient.getArticle(articleId);
            return articleDetail;
        } catch (Exception e) {
            log.error("ArticleReadCacheRepository.getArticleDetailDto articleClient.getArticle fail error", e);
            throw new CustomException(ErrorCode.ARTICLE_READ_NOT_FOUNT);
        }
    }

    @Override
    public List<ArticleRead> readAll(List<Long> articleIds) {
        return articleIds.stream()
                .map(a->readNotException(a))
                .collect(Collectors.toList());
    }

    private  ArticleRead createNotException(ArticleRead articleRead) {
        try {
            return create(articleRead);
        } catch (Exception e){
            log.error("ArticleReadCacheRepository.createNotException cache save fail error", e);
        }
        return null;
    }
    private ArticleRead readNotException(Long articleId) {
        try {
            return read(articleId);
        } catch (Exception e){
            log.error("ArticleReadCacheRepository.readNotException read fail error", e);
        }
        return null;
    }

    private String generateKey(Long articleId) {
        return KEY_FORMAT.formatted(articleId);
    }
}
