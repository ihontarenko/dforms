package df.base.validation;

import df.base.common.jpa.FieldSet;
import df.base.common.jpa.JpaHelper;
import df.base.security.UserInfo;
import df.base.validation.constrain.ResourceExistence;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.function.Predicate;

public class ResourceExistenceValidator implements ConstraintValidator<ResourceExistence, Object> {

    private final JpaHelper  jpaHelper;
    private       Class<?>   entityClass;
    private       FieldSet[] fields;
    private       String     existence;
    private       String     target;
    private       boolean    unique;
    private       boolean    reverse;
    private       String     authority;

    public ResourceExistenceValidator(JpaHelper jpaHelper) {
        this.jpaHelper = jpaHelper;
    }

    @Override
    public void initialize(ResourceExistence annotation) {
        this.authority = annotation.authority();
        this.existence = annotation.existence();
        this.fields = annotation.fields();
        this.entityClass = annotation.entityClass();
        this.reverse = annotation.reverse();
        this.unique = annotation.unique();
        this.target = annotation.target();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        Object  idValue            = jpaHelper.objectValue(object, existence);
        boolean isValid            = true;
        boolean validationRequired = idValue == null;

        if (!validationRequired && (idValue instanceof String)) {
            validationRequired = ((String) idValue).isBlank();
        }

        validationRequired = validationRequired == !reverse;

        if (validationRequired && !authority.isBlank()) {
            Authentication    authentication = SecurityContextHolder.getContext().getAuthentication();
            UserInfo          principal      = (UserInfo) authentication.getPrincipal();
            Predicate<String> predicate      = authority -> authority.equals(this.authority);

            validationRequired = principal.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .anyMatch(predicate.negate());
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
        TypedQuery<Object> typedQuery = jpaHelper.createTypedQuery((Class<Object>) entityClass, fields, object);
        List<Object>       resultSet  = typedQuery.getResultList();

        return unique == (resultSet.size() == 0);
    }

    private boolean isAuthorityGranted() {
        Authentication    authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo          principal      = (UserInfo) authentication.getPrincipal();
        Predicate<String> predicate      = authority -> authority.equals(this.authority);

        return principal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(predicate.negate());
    }

    private void addConstraintViolation(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        String                     messageTemplate  = context.getDefaultConstraintMessageTemplate();
        ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(messageTemplate);
        violationBuilder.addPropertyNode(target).addConstraintViolation();
    }

}