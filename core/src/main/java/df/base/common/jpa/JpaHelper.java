package df.base.common.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public final class JpaHelper {

    public static TypedQuery<Object> createQuery(EntityManager em, Class<?> entityClass, FieldSet[] fields, Object object) {
        CriteriaBuilder       criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery   = criteriaBuilder.createQuery();
        Root<?>               root            = criteriaQuery.from(entityClass);
        List<Predicate>       predicates      = new ArrayList<>();

        for (FieldSet field : fields) {
            BiFunction<Expression<?>, Object, Predicate> expression = switch (field.comparison()) {
                case NEQ -> criteriaBuilder::notEqual;
                case EQ -> criteriaBuilder::equal;
            };

            predicates.add(expression.apply(resolveExpression(field.entityName(), root),
                    objectValue(object, field.objectName())));
        }

        criteriaQuery.where(predicates.toArray(Predicate[]::new));

        return em.createQuery(criteriaQuery);
    }

    private static Expression<?> resolveExpression(String entityField, Root<?> root) {
        if (entityField.indexOf('.') != -1) {
            String child = entityField.substring(0, entityField.indexOf('.'));
            String field = entityField.substring(entityField.indexOf('.') + 1);
            return root.join(child).get(field);
        }

        return root.get(entityField);
    }

    public static Object objectValue(Object object, String fieldName) {
        Class<?> targetClass = object.getClass();
        Object   fieldValue  = null;

        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            fieldValue = field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException ignore) {

        }

        return fieldValue;
    }

}
