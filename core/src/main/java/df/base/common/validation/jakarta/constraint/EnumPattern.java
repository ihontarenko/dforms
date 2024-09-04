package df.base.common.validation.jakarta.constraint;

import df.base.common.validation.jakarta.EnumPatternValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumPatternValidator.class)
public @interface EnumPattern {

    String regexp();

    String message() default "must match '{regexp}'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}