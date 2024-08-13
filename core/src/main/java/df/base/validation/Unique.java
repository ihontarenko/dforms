package df.base.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = {UniqueValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {

    String keyExistence() default "";

    String targetField();

    Field[] fields();

    Class<?> entityClass();

    boolean checkUnique() default true;

    boolean reverseExistence() default false;

    String superUserRole() default "";

    String message() default "unique value constraint violation. unique: {unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface Set {

        Unique[] value();

    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface Field {

        String objectName();

        String entityName() default "";

    }

}