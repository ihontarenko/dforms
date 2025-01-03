package df.common.reflection;

import df.common.matcher.Matcher;

import java.lang.reflect.Constructor;

public class ConstructorFilter extends AbstractFilter<Constructor<?>> {

    public ConstructorFilter(MemberFinder<Constructor<?>> finder, Matcher<Constructor<?>> matcher, Class<?> type) {
        super(finder, matcher, type);
    }

}