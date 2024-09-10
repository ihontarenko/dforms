package df.base.specification;

import df.base.common.specification.Specification;
import df.base.common.specification.SpecificationContext;
import df.base.persistence.entity.form.Field;
import df.base.persistence.exception.JpaResourceIneligibleException;
import org.springframework.stereotype.Component;

import java.util.List;

import static df.base.Messages.EXCEPTION_CUSTOMIZATION_INELIGIBLE_FIELD;
import static df.base.persistence.entity.support.ElementType.*;
import static java.util.Objects.requireNonNull;

@Component
public class FieldSelectiveSpecification implements Specification<Field> {

    @Override
    public boolean isSatisfied(Field field, SpecificationContext context) {
        if (requireNonNull(context.getAttribute("section")).equals("option")) {
            return List.of(CHECKBOX, RADIO, SELECT).contains(field.getElementType());
        }
        return true;
    }

    @Override
    public RuntimeException getExceptionForViolation(Field field, SpecificationContext context) {
        String redirect = "%s#%s".formatted(context.getAttribute("redirect"),
                getClass().getSimpleName());
        return new JpaResourceIneligibleException(EXCEPTION_CUSTOMIZATION_INELIGIBLE_FIELD
                .formatted(field.getElementType(), field.getUsageType()), redirect);
    }

}
