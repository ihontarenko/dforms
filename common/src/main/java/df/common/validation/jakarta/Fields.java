package df.common.validation.jakarta;

import df.common.support.jpa.JpaCriteria;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Fields {

    Value objectValue();

    String entityField();

    JpaCriteria.Comparison comparison() default JpaCriteria.Comparison.EQUAL;

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface Value {

        String value();

        ValueType type();

    }

    enum ValueType {
        RAW_VALUE, FIELD_NAME, SPEL_EXPRESSION
    }

}
