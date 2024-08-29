package df.base.internal.breadcrumb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Breadcrumbs {

    Item[] value() default {};

    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    @interface Item {

        String label();

        String url() default "";

    }

}
