package jhkim593.springboard.articleread.cache;

@FunctionalInterface
public interface OptimizedCacheOriginDataSupplier<T> {
    T get() throws Throwable;
}
