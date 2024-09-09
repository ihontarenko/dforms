package df.base.common.specification;

final public class SpecificationRunner<T> {
    public void checkAllSatisfied(T object, SpecificationContext context, Specification<T>... specifications) {
        for (Specification<T> specification : specifications) {
            specification.checkSatisfied(object, context);
        }
    }
}