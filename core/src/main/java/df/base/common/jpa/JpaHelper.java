package df.base.common.jpa;

import df.base.common.application_context.ReflectionUtils;
import df.base.common.jpa.FieldSet.Comparison;
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

    public JpaHelper(EntityManager em) {
        this.em = em;
    }

    @SuppressWarnings({"unchecked"})
    public <E> TypedQuery<E> createTypedQuery(Class<E> entityClass, FieldSet[] fields, Object object) {
        CriteriaBuilder       builder = em.getCriteriaBuilder();
        CriteriaQuery<Object> query   = builder.createQuery();
        Root<E>               root    = query.from(entityClass);

        query.where(createPredicates(fields, builder, root, object).toArray(Predicate[]::new));

        return (TypedQuery<E>) em.createQuery(query);
    }

    public List<Predicate> createPredicates(FieldSet[] fields, CriteriaBuilder builder, Root<?> root, Object object) {
        List<Predicate> predicates = new ArrayList<>();

        for (FieldSet field : fields) {
            Object        value      = objectValue(object, field.objectName());
            Expression<?> expression = resolveExpression(field.entityName(), root);
            Predicate     predicate  = resolveQueryExpression(field.comparison(), builder).apply(expression, value);

            predicates.add(predicate);
        }

        return predicates;
    }

    private BiFunction<Expression<?>, Object, Predicate> resolveQueryExpression(Comparison comparison,
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

    public Object objectValue(Object object, String name) {
        return ReflectionUtils.getFieldValue(object, name);
    }

}
