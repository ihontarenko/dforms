package df.base.common.specification;

public interface Specification<T> {
    boolean isSatisfied(T t, SpecificationContext context);

    default void checkSatisfied(T t, SpecificationContext context) throws RuntimeException {
        if (!isSatisfied(t, context)) {
            throw getExceptionForViolation(t, context);
        }
    }

    default RuntimeException getExceptionForViolation(T t, SpecificationContext context) {
        return new RuntimeException("Object '%s' is unsatisfied for '%s'".formatted(t.getClass(), getClass()));
    }
}
