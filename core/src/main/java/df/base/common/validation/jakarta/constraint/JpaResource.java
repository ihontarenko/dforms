package df.base.common.validation.jakarta.constraint;

import df.base.common.validation.jakarta.Fields;
import df.base.common.validation.jakarta.JpaResourceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = {JpaResourceValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JpaResource {

    // SpEL expression
    String applier() default "";

    // SpEL expression
    String predicate() default "#result.empty";

    String pointer();

    Fields[] fields();

    Class<?> entityClass();

    String message() default "JPA resource constraint violation";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {

        JpaResource[] value();

    }

}