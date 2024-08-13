package df.base.validation;

import df.base.security.UserInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
    private       String         superUserRole;

    public UniqueValidator(EntityManager em) {
        this.em = em;
    }

    @Override
    public void initialize(Unique annotation) {
        this.superUserRole = annotation.superUserRole();
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

        if (validationRequired && !superUserRole.isBlank()) {
            validationRequired = checkSuperUser();
        }

        if (validationRequired) {
            isValid = doValidate(object);
        }

        if (!isValid) {
            addConstraintViolation(context);
        }

        return isValid;
    }

    private boolean doValidate(Object object) {
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

        return checkUnique == (resultSet.size() == 0);
    }

    private boolean checkSuperUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo       principal      = (UserInfo) authentication.getPrincipal();

        return principal.getUser().getRoles()
                .stream().anyMatch(role -> role.getName().equals(superUserRole));
    }

    private void addConstraintViolation(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        String                     messageTemplate  = context.getDefaultConstraintMessageTemplate();
        ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(messageTemplate);
        violationBuilder.addPropertyNode(targetField).addConstraintViolation();
    }

    private Expression<?> resolveExpression(Unique.Field annotation, Root<?> root) {
        String path = annotation.entityName();

        if (path.indexOf('.') != -1) {
            String child = path.substring(0, path.indexOf('.'));
            String field = path.substring(path.indexOf('.') + 1);
            return root.join(child).get(field);
        }

        return root.get(path);
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