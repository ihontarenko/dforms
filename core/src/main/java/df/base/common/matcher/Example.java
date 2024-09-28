package df.base.common.matcher;

import df.base.common.libs.jbm.ReflectionUtils;
import df.base.common.reflection.FieldFinder;
import df.base.common.reflection.Finder;
import df.base.persistence.entity.form.FieldConfig;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static df.base.common.matcher.MatchContext.createDefault;
import static df.base.common.matcher.reflection.FieldMatchers.isAnnotatedWith;

public class Example {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MatchContext context = createDefault();

        System.out.println("v1");
        for (Field allAnnotatedField : ReflectionUtils.findAllAnnotatedFields(FieldConfig.class, Column.class)) {
            System.out.println(allAnnotatedField);
        }

        Finder<Field>  finder  = new FieldFinder();
        Matcher<Field> matcher = Matcher.or(isAnnotatedWith(Column.class), isAnnotatedWith(ManyToOne.class));

        System.out.println("finder + matcher");
        for (Field field : finder.find(FieldConfig.class, matcher, context)) {
            System.out.println(field);
        }

    }
}

