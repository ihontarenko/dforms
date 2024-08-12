package df.base.validation;

import df.base.security.UserInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizationIdValidator implements ConstraintValidator<AuthorizationId, String> {

    @Override
    public boolean isValid(String authorizedID, ConstraintValidatorContext constraintValidatorContext) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo       principal      = (UserInfo) authentication.getPrincipal();

        return authorizedID != null && authorizedID.equals(principal.getUser().getId());
    }

}
