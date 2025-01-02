package df.base.common.observer;

import df.base.common.container.StringUtils;
import df.base.common.matcher.Matcher;
import df.base.common.matcher.reflection.TypeMatchers;

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
