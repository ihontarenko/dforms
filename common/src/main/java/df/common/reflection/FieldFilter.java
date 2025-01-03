package df.common.reflection;

import df.common.matcher.Matcher;

import java.lang.reflect.Field;

public class FieldFilter extends AbstractFilter<Field> {

    public FieldFilter(MemberFinder<Field> finder, Matcher<Field> matcher, Class<?> type) {
        super(finder, matcher, type);
    }

}
