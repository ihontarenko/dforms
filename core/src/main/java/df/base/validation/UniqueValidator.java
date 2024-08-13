package df.base.validation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private final EntityManager  em;
    private       Class<?>       entityClass;
    private       Unique.Field[] fields;
    private       String         objectKey;
    private       String         targetField;
    private       boolean        checkUnique;
    private       boolean        keyReverse;

    public UniqueValidator(EntityManager em) {
        this.em = em;
    }

    @Override
    public void initialize(Unique annotation) {
        this.objectKey = annotation.objectKey();
        this.fields = annotation.fields();
        this.entityClass = annotation.entityClass();
        this.keyReverse = annotation.keyReverse();
        this.checkUnique = annotation.checkUnique();
        this.targetField = annotation.targetField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        Object  idValue            = getFieldValue(object, objectKey);
        boolean isValid            = true;
        boolean validationRequired = idValue == null;

        if (!validationRequired && (idValue instanceof String)) {
            validationRequired = ((String) idValue).isBlank();
        }

        validationRequired = validationRequired == !keyReverse;

        if (validationRequired) {
            CriteriaBuilder       criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Object> criteriaQuery   = criteriaBuilder.createQuery();
            Root<?>               root            = criteriaQuery.from(entityClass);
            List<Predicate>       predicates      = new ArrayList<>();

            for (Unique.Field field : fields) {
                predicates.add(criteriaBuilder.equal(resolveExpression(field, root),
                        getFieldValue(object, field.objectName())));
            }

            criteriaQuery.where(predicates.toArray(Predicate[]::new));

            TypedQuery<Object> typedQuery = em.createQuery(criteriaQuery);
            List<Object>       resultSet  = typedQuery.getResultList();

            isValid = checkUnique == (resultSet.size() == 0);
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            String                     messageTemplate  = context.getDefaultConstraintMessageTemplate();
            ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(messageTemplate);
            violationBuilder.addPropertyNode(targetField).addConstraintViolation();
        }

        return isValid;
    }

    private Expression<?> resolveExpression(Unique.Field annotation, Root<?> root) {
        String        path = annotation.entityName();
        Expression<?> expression;

        if (path.indexOf('.') == -1) {
            expression = root.get(path);
        } else {
            String child = path.substring(0, path.indexOf('.'));
            String field = path.substring(path.indexOf('.') + 1);
            expression = root.join(child).get(field);
        }

        return expression;
    }

    private Object getFieldValue(Object object, String fieldName) {
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