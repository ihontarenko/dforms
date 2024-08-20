package df.base.validation.constraint;

import df.base.common.jpa.FieldSet;
import df.base.validation.ResourceExistenceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = {ResourceExistenceValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceExistence {

    String existence() default "";

    String target();

    FieldSet[] fields();

    Class<?> entityClass();

    boolean unique() default true;

    boolean invert() default false;

    String authority() default "";

    String message() default "unique value constraint violation";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface Set {

        ResourceExistence[] value();

    }

}