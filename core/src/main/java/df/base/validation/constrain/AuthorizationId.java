package df.base.validation.constrain;

import df.base.validation.AuthorizationIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AuthorizationIdValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizationId {

    String message() default "must be equals to authorized ID";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
