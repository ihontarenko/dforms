package df.application.specification;

import df.common.specification.Specification;
import df.common.specification.SpecificationContext;
import df.application.persistence.entity.form.Field;
import df.application.persistence.entity.support.UsageType;
import df.application.persistence.exception.JpaResourceIneligibleException;
import org.springframework.stereotype.Component;

import static df.application.Messages.EXCEPTION_EMBEDDED_INELIGIBLE_FIELD;
import static java.util.Objects.requireNonNull;

@Component
public class FieldTypeSpecification implements Specification<Field> {

    @Override
    public boolean isSatisfied(Field field, SpecificationContext context) {
        if (requireNonNull(context.getAttribute("section")).equals("embedded")) {
            return field.getUsageType() == UsageType.VIRTUAL;
        }
        return true;
    }

    @Override
    public RuntimeException getExceptionForViolation(Field field, SpecificationContext context) {
        String redirect = "%s#%s".formatted(context.getAttribute("redirect"),
                getClass().getSimpleName());
        return new JpaResourceIneligibleException(EXCEPTION_EMBEDDED_INELIGIBLE_FIELD,
                context.getAttribute(redirect));
    }

}
