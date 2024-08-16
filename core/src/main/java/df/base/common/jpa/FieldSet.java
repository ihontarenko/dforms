package df.base.common.jpa;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldSet {

    String objectName();

    String entityName() default "";

    Comparison comparison() default Comparison.EQUAL;

    enum Comparison {
        EQUAL, NOT_EQUAL, GTE, GT
    }

}
