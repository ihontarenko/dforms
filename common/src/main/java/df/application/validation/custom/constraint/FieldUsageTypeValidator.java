package df.application.validation.custom.constraint;

import df.application.validation.custom.FieldErrorCode;
import df.application.dto.form.FieldDTO;
import df.application.persistence.entity.support.ElementType;
import df.application.persistence.entity.support.UsageType;
import org.jmouse.validator.old.AbstractValidator;
import org.jmouse.validator.old.Errors;
import org.jmouse.validator.old.ValidationContext;
import org.jmouse.validator.old.ValidationException;

import static df.application.persistence.support.EntityConstants.FIELD_FIELD_USAGE_TYPE;

public class FieldUsageTypeValidator extends AbstractValidator {

    @Override
    public void validate(Object object, Errors errors, ValidationContext context) throws ValidationException {
        FieldDTO    fieldDTO    = (FieldDTO) object;

        if (fieldDTO.getElementType() == null || fieldDTO.getUsageType() == null) {
            throw new ValidationException(FIELD_FIELD_USAGE_TYPE, FieldErrorCode.INCOMPATIBLE_USAGE_TYPE,
                                          "could not check compatibility of element type with field type due to empty DTO");
        }

        ElementType elementType = ElementType.valueOf(fieldDTO.getElementType());
        UsageType   usageType   = UsageType.valueOf(fieldDTO.getUsageType());

        if (usageType == UsageType.VIRTUAL && elementType != ElementType.NONE) {
            errors.rejectValue(FIELD_FIELD_USAGE_TYPE, "a VIRTUAL column must have an element type of NONE");
        }
    }

    @Override
    public boolean supports(Object object) {
        return true;
    }

}
