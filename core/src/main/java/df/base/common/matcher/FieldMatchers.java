package df.base.common.matcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FieldMatchers {

    public static Matcher<Field> isPublic() {
        return new FieldModifierMatcher(Modifier.PUBLIC);
    }

    public static Matcher<Field> isAnnotatedWith(Class<? extends Annotation> annotation) {
        return new FieldAnnotatedMatcher(annotation);
    }

    public static Matcher<Field> hasType(Class<?> type) {
        return new FieldTypeMatcher(type);
    }

    public static Matcher<Field> nameStarts(String prefix) {
        return withName(TextMatchers.startsWith(prefix));
    }

    public static Matcher<Field> nameEnds(String suffix) {
        return withName(TextMatchers.endsWith(suffix));
    }

    public static Matcher<Field> nameContains(String substring) {
        return withName(TextMatchers.contains(substring));
    }

    public static Matcher<Field> nameEquals(String actual) {
        return withName(TextMatchers.same(actual));
    }

    public static Matcher<Field> withName(Matcher<? super String> textMatcher) {
        return new FieldNameWithMatcher(textMatcher);
    }

    static class FieldNameWithMatcher implements Matcher<Field> {

        private final Matcher<? super String> textMatcher;

        FieldNameWithMatcher(Matcher<? super String> textMatcher) {
            this.textMatcher = textMatcher;
        }

        @Override
        public boolean matches(Field item, MatchContext context) {
            return textMatcher.matches(item.getName(), context);
        }

    }

    static class FieldModifierMatcher implements Matcher<Field> {

        private final int modifier;

        public FieldModifierMatcher(int modifier) {
            this.modifier = modifier;
        }

        @Override
        public boolean matches(Field field, MatchContext context) {
            return (field.getModifiers() & modifier) != 0;
        }

    }

    static class FieldAnnotatedMatcher implements Matcher<Field> {

        private final Class<? extends Annotation> annotation;

        public FieldAnnotatedMatcher(Class<? extends Annotation> annotation) {
            this.annotation = annotation;
        }

        @Override
        public boolean matches(Field field, MatchContext context) {
            return field.isAnnotationPresent(annotation);
        }

    }

    static class FieldTypeMatcher implements Matcher<Field> {

        private final Class<?> type;

        public FieldTypeMatcher(Class<?> type) {
            this.type = type;
        }

        @Override
        public boolean matches(Field field, MatchContext context) {
            return field.getType().equals(type);
        }

    }

}
