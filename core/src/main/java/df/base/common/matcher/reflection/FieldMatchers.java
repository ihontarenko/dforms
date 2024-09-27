package df.base.common.matcher.reflection;

import df.base.common.matcher.MatchContext;
import df.base.common.matcher.Matcher;
import df.base.common.matcher.TextMatchers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@SuppressWarnings({"unused"})
public class FieldMatchers {

    public static Matcher<Field> withModifier(int modifier) {
        return new FieldModifierMatcher(modifier);
    }

    public static Matcher<Field> isPrivate() {
        return withModifier(Modifier.PRIVATE);
    }

    public static Matcher<Field> isProtected() {
        return withModifier(Modifier.PROTECTED);
    }

    public static Matcher<Field> isPublic() {
        return withModifier(Modifier.PUBLIC);
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

    private record FieldNameWithMatcher(Matcher<? super String> textMatcher) implements Matcher<Field> {
        @Override
        public boolean matches(Field item, MatchContext context) {
            return textMatcher.matches(item.getName(), context);
        }
    }

    private record FieldModifierMatcher(int modifier) implements Matcher<Field> {
        @Override
        public boolean matches(Field field, MatchContext context) {
            return (field.getModifiers() & modifier) != 0;
        }
    }

    private record FieldAnnotatedMatcher(Class<? extends Annotation> annotation) implements Matcher<Field> {
        @Override
        public boolean matches(Field field, MatchContext context) {
            return field.isAnnotationPresent(annotation);
        }
    }

    private record FieldTypeMatcher(Class<?> type) implements Matcher<Field> {
        @Override
        public boolean matches(Field field, MatchContext context) {
            return field.getType().equals(type);
        }
    }

}
