package jhkim593.springboard.articleread.cache;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OptimizedCacheTTL {
    private Duration logicalTTL;
    private LocalDateTime expiredAt;
    public static final long PHYSICAL_TTL_DELAY_SECONDS = 5;

    public OptimizedCacheTTL(Duration logicalTTL) {
        this.logicalTTL = logicalTTL;
        this.expiredAt = LocalDateTime.now().plus(logicalTTL);
    }

    public static OptimizedCacheTTL create(Duration logicalTTL) {
        return new OptimizedCacheTTL(logicalTTL);
    }

    public LocalDateTime expiredAt() {
        return expiredAt;
    }
    public Duration physicalTTl() {
        return logicalTTL.plusSeconds(PHYSICAL_TTL_DELAY_SECONDS);
    }
}
