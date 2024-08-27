package df.base.validation.hibernate.constraint;

import df.base.validation.hibernate.SpelConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = {SpelConstraintValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpelConstraint {

    String value();

    String pointer();

    String applier() default "";

    Class<?>[] functions() default {};

    String message() default "{SpELConstraint.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {

        SpelConstraint[] value();

    }

}
