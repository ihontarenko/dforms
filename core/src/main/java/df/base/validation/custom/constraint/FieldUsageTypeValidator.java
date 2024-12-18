package df.base.validation.custom.constraint;

import df.base.common.validation.custom.*;
import df.base.dto.form.FieldDTO;
import df.base.persistence.entity.support.ElementType;
import df.base.persistence.entity.support.UsageType;

import static df.base.persistence.support.EntityConstants.FIELD_FIELD_USAGE_TYPE;
import static df.base.validation.custom.FieldErrorCode.INCOMPATIBLE_USAGE_TYPE;

public class FieldUsageTypeValidator extends AbstractValidator {

    @Override
    public void validate(Object object, Errors errors, ValidationContext context) throws ValidationException {
        FieldDTO    fieldDTO    = (FieldDTO) object;

        if (fieldDTO.getElementType() == null || fieldDTO.getUsageType() == null) {
            throw new ValidationException(FIELD_FIELD_USAGE_TYPE, INCOMPATIBLE_USAGE_TYPE,
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
