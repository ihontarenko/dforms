package df.common.validation.jakarta.constraint;

import df.common.validation.jakarta.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {

    String message() default "password is not strong enough";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String special() default "!@#$%^&*()_+";

}
