package df.base.common.reflection;

import df.base.common.matcher.MatchContext;
import df.base.common.matcher.Matcher;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFinder<T extends Member> implements Finder<T> {

    protected abstract T[] getMembers(Class<?> clazz);

    @Override
    public List<T> find(Class<?> clazz, Matcher<? super T> matcher, MatchContext context) {
        List<T> matchedMembers = new ArrayList<>();

        for (T member : getMembers(clazz)) {
            if (matcher.matches(member, context)) {
                matchedMembers.add(member);
            }
        }

        return matchedMembers;
    }

}