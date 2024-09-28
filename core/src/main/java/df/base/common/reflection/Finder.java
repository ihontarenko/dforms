package df.base.common.reflection;

import df.base.common.matcher.MatchContext;
import df.base.common.matcher.Matcher;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

public interface Finder<T extends Member> {

    List<T> find(Class<?> clazz, Matcher<? super T> matcher, MatchContext context);

    default Optional<T> findFirst(Class<?> clazz, Matcher<? super T> matcher, MatchContext context) {
        return find(clazz, matcher, context).stream().findFirst();
    }

}