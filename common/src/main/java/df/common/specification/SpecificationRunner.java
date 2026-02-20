package df.common.specification;

final public class SpecificationRunner<T> {
    @SafeVarargs
    public final void checkAllSatisfied(T object, SpecificationContext context, Specification<T>... specifications) {
        for (Specification<T> specification : specifications) {
            specification.checkSatisfied(object, context);
        }
    }
}