package jhkim593.springboard.articleread.cache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jhkim593.springboard.common.core.util.DataSerializer;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@ToString
public class OptimizedCache {
    private String data;
    private OptimizedCacheTTL ttl;

    public static OptimizedCache of(Object data, Duration ttl) {
        OptimizedCache optimizedCache = new OptimizedCache();
        optimizedCache.data = DataSerializer.serialize(data);
        optimizedCache.ttl = OptimizedCacheTTL.create(ttl);
        return optimizedCache;
    }

    @JsonIgnore
    public boolean isNotExpired() {
        return !LocalDateTime.now().isAfter(ttl.expiredAt());
    }

    public Duration physicalTTL() {
        return ttl.physicalTTl();
    }

    public <T> T parseData(Class<T> dataType) {
        return DataSerializer.deserialize(data, dataType);
    }
}
