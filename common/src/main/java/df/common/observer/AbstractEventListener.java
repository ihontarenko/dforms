package df.common.observer;

import df.common.container.StringUtils;
import df.common.matcher.Matcher;
import df.common.matcher.reflection.TypeMatchers;

abstract public class AbstractEventListener<T> implements EventListener<T> {

    protected final Matcher<Class<?>> matcher = TypeMatchers.isSimilar(applicableType());

    @Override
    public String name() {
        return StringUtils.underscored(getClass().getSimpleName(), true);
    }

    @Override
    public boolean supports(Class<?> actualType) {
        return matcher.matches(actualType);
    }

}
