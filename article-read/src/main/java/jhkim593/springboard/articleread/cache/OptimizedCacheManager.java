package jhkim593.springboard.articleread.cache;

import jhkim593.springboard.common.core.util.DataSerializer;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.joining;

@Component
@RequiredArgsConstructor
public class OptimizedCacheManager {
//    private final CacheRepository cacheRepository;
    private final StringRedisTemplate redisTemplate;
    private final RedissonClient redissonClient;

    private static final String DELIMITER = "::";

    public Object process(String type, long ttlSeconds, Object[] args, Class<?> returnType,
                          OriginDataSupplier<?> originDataSupplier) throws Throwable {
        String key = generateKey(type, args);

        //physical ttl 만료
        String cachedData = redisTemplate.opsForValue().get(key);
        if(cachedData == null){
            RLock lock = redissonClient.getLock(key);
            try {
                boolean lockable = lock.tryLock(5, 2, TimeUnit.SECONDS);
                if (!lockable) return redisTemplate.opsForValue().get(key);

                //double check
                cachedData = redisTemplate.opsForValue().get(key);
                if (cachedData == null) {
                    return refresh(originDataSupplier, key, ttlSeconds);
                }
                OptimizedCache optimizedCache = DataSerializer.deserialize(cachedData, OptimizedCache.class);
                return optimizedCache.parseData(returnType);
            } finally {
                if(lock!= null && lock.isHeldByCurrentThread()) lock.unlock();
            }
        }


        OptimizedCache optimizedCache = DataSerializer.deserialize(cachedData, OptimizedCache.class);
        if(optimizedCache.isNotExpired()){
            return optimizedCache.parseData(returnType);
        }

        RLock lock = redissonClient.getLock(key);
        try {
            boolean lockable = lock.tryLock(0, 2, TimeUnit.SECONDS);
            if (!lockable) return optimizedCache.parseData(returnType);
            return refresh(originDataSupplier, key, ttlSeconds);
        } finally {
            if(lock!= null && lock.isHeldByCurrentThread()) lock.unlock();
        }
    }

    private Object refresh(OriginDataSupplier<?> originDataSupplier, String key, long ttlSeconds) throws Throwable {
        Object result = originDataSupplier.get();

        OptimizedCache optimizedCache = OptimizedCache.of(result, Duration.ofSeconds(ttlSeconds));
        redisTemplate.opsForValue()
                .set(key, DataSerializer.serialize(optimizedCache), optimizedCache.physicalTTL());

        return result;
    }

    private String generateKey(String prefix, Object[] args) {
        return prefix + DELIMITER +
                Arrays.stream(args)
                        .map(String::valueOf)
                        .collect(joining(DELIMITER));
    }

}
