package df.base.common.jpa;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldSet {

    String objectField() default "";

    String objectValue() default "";

    String entityField() default "";

    Comparison comparison() default Comparison.EQUAL;

    enum Comparison {
        EQUAL, NOT_EQUAL, GTE, GT
    }

}
