package df.base.common.reflection;

import df.base.common.matcher.MatchContext;
import df.base.common.matcher.Matcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static df.base.common.matcher.reflection.FieldMatchers.isAnnotatedWith;

public class FieldFinder extends AbstractFinder<Field> {

    @SafeVarargs
    public static Field[] getAnnotatedWith(Class<?> clazz, Class<? extends Annotation>... annotations) {
        Finder<Field>  finder  = new FieldFinder();
        Matcher<Field> matcher = Matcher.empty(false);

        for (Class<? extends Annotation> annotation : annotations) {
            matcher = matcher.or(isAnnotatedWith(annotation));
        }

        return finder.find(clazz, matcher, MatchContext.createDefault()).toArray(Field[]::new);
    }

    @Override
    protected Field[] getMembers(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

}
