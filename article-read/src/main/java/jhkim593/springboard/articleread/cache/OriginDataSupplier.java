package jhkim593.springboard.articleread.cache;

@FunctionalInterface
public interface OriginDataSupplier<T> {
    T get() throws Throwable;
}
