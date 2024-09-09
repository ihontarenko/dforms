package df.base.validation.custom.constraint;

import df.base.common.validation.custom.ValidationContext;
import df.base.common.validation.custom.ValidationException;
import df.base.common.validation.custom.Validator;
import df.base.dto.form.FieldDTO;
import df.base.persistence.entity.support.ElementType;
import df.base.persistence.entity.support.UsageType;

import static df.base.validation.custom.FieldErrorCode.INCOMPATIBLE_USAGE_TYPE;

public class FieldUsageTypeValidator implements Validator {

    @Override
    public void validate(Object object, ValidationContext validationContext) throws ValidationException {
        FieldDTO    fieldDTO    = (FieldDTO) object;

        if (fieldDTO.getElementType() == null || fieldDTO.getUsageType() == null) {
            throw new ValidationException(INCOMPATIBLE_USAGE_TYPE,
                    "could not check compatibility of element type with field type due to empty DTO");
        }

        ElementType elementType = ElementType.valueOf(fieldDTO.getElementType());
        UsageType   usageType   = UsageType.valueOf(fieldDTO.getUsageType());

        if (usageType == UsageType.VIRTUAL && elementType != ElementType.NONE) {
            throw new ValidationException(INCOMPATIBLE_USAGE_TYPE);
        }
    }

    @Override
    public boolean supports(Class<?> objectType) {
        return FieldDTO.class.isAssignableFrom(objectType);
    }

}
