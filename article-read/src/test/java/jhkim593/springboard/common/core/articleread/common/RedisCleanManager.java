package jhkim593.springboard.common.core.articleread.common;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisCleanManager {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCleanManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void execute() {
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
    }
}