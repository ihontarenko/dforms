package df.base.common.support;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Service
public final class JpaHelper {

    public final EntityManager em;

    public JpaHelper(EntityManager em, SpELEvaluator evaluator) {
        this.em = em;
    }

    @SuppressWarnings({"unchecked"})
    public <E> TypedQuery<E> createTypedQuery(Class<E> entityClass, JpaCriteria[] criteria) {
        CriteriaBuilder       builder = em.getCriteriaBuilder();
        CriteriaQuery<Object> query   = builder.createQuery();
        Root<E>               root    = query.from(entityClass);

        query.where(createPredicates(criteria, builder, root).toArray(Predicate[]::new));

        return (TypedQuery<E>) em.createQuery(query);
    }

    public List<Predicate> createPredicates(JpaCriteria[] criteria, CriteriaBuilder builder, Root<?> root) {
        List<Predicate> predicates = new ArrayList<>();

        for (JpaCriteria jpaCriteria : criteria) {
            Expression<?> expression = resolveExpression(jpaCriteria.entityField(), root);
            Predicate     predicate  = resolveQueryExpression(jpaCriteria.comparison(), builder)
                    .apply(expression, jpaCriteria.entityValue());

            predicates.add(predicate);
        }

        return predicates;
    }

    private BiFunction<Expression<?>, Object, Predicate> resolveQueryExpression(JpaCriteria.Comparison comparison,
                                                                                CriteriaBuilder builder) {
        return switch (comparison) {
            case GTE -> (expression, object)
                    -> builder.greaterThanOrEqualTo(expression.as(String.class), (String) object);
            case GT -> (expression, object)
                    -> builder.greaterThan(expression.as(String.class), (String) object);
            case NOT_EQUAL -> builder::notEqual;
            case EQUAL -> builder::equal;
        };
    }

    private Expression<?> resolveExpression(String path, Root<?> root) {
        if (path.contains(".")) {
            return resolveNestedExpression(path, root);
        }

        return root.get(path);
    }

    private Expression<?> resolveNestedExpression(String path, Root<?> root) {
        String[]   paths = path.split("\\.");
        From<?, ?> from  = root;

        for (int i = 0; i < paths.length - 1; i++) {
            from = from.join(paths[i]);
        }

        return from.get(paths[paths.length - 1]);
    }

}
