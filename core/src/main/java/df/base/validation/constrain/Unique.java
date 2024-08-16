package df.base.validation.constrain;

import df.base.common.jpa.FieldSet;
import df.base.validation.UniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = {UniqueValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {

    String existence() default "";

    String target();

    FieldSet[] fields();

    Class<?> entityClass();

    boolean unique() default true;

    boolean reverse() default false;

    String authority() default "";

    String message() default "unique value constraint violation. unique: {unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface Set {

        Unique[] value();

    }

}