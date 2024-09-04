package df.base.common.extensions.hibernate.generator;

import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IdGeneratorType(PrefixedTableSequenceGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface PrefixedId {

    String prefixValue();

    Class<? extends IdPrefixGenerator> prefixGenerator() default IdPrefixGenerator.Default.class;

    String prefixSeparator() default "-";

    String numberFormat() default "%09d";

    String sequenceName() default "DEFAULT";

    String keyColumnName() default "SEQUENCE_NAME";

    String valueColumnName() default "NEXT_VALUE";

    String tableName() default "ID_GENERATOR";

    int initialValue() default 1;

    int incrementBy() default 50;

}
