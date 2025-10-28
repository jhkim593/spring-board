package jhkim593.springboard.articleread.cache;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OptimizedCacheTTL {
    private final LocalDateTime logicalTTL;
    public static final long PHYSICAL_TTL_DELAY_SECONDS = 3;

    public OptimizedCacheTTL(LocalDateTime logicalTTL) {
        this.logicalTTL = logicalTTL;
    }

    public static OptimizedCacheTTL create(LocalDateTime logicalTTL) {
        return new OptimizedCacheTTL(logicalTTL);
    }

    public LocalDateTime logicalTTL() {
        return logicalTTL;
    }
    public LocalDateTime physicalTTL() {
        return logicalTTL.plusSeconds(PHYSICAL_TTL_DELAY_SECONDS);
    }
}
