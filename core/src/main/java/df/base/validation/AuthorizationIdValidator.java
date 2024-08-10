package df.base.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AuthorizationIdValidator implements ConstraintValidator<AuthorizationId, String> {

    @Override
    public boolean isValid(String authorizedID, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }

}
