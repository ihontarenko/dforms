package df.base.common.function;

@FunctionalInterface
public interface Mapper<F, T> {
    T map(F source);
}
