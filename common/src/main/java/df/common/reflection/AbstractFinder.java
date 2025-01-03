package df.common.reflection;

import df.common.matcher.Matcher;
import df.common.matcher.reflection.MemberMatcher;

import java.lang.reflect.Member;
import java.util.Collection;
import java.util.List;

/**
 * An abstract class that implements the base logic for finding class members (fields, methods, constructors)
 * based on specific matching conditions defined by {@link Matcher}.
 *
 * @param <T> the type of member being searched (e.g., Field, Method, Constructor)
 */
public abstract class AbstractFinder<T extends Member> implements MemberFinder<T> {

    /**
     * Returns a collection of members from the specified class.
     * This method should be implemented by subclasses to define which members are to be searched
     * (e.g., methods, fields, constructors).
     *
     * @param clazz the class whose members should be retrieved
     * @return a collection of members from the class
     */
    protected abstract Collection<T> getMembers(Class<?> clazz);

    /**
     * Returns a collection of members from the specified class.
     * This method should be implemented by subclasses to define which members are to be searched
     * (e.g., methods, fields, constructors).
     *
     * @param clazz the class whose members should be retrieved
     * @param deepScan whether to scan superclasses for members
     * @return a collection of members from the class
     */
    protected abstract Collection<T> getMembers(Class<?> clazz, boolean deepScan);

    /**
     * Finds all members of the given class that match the specified {@link Matcher}.
     * This method iterates over all the members retrieved by {@link #getMembers(Class)} and applies the matcher.
     *
     * @param clazz   the class whose members are to be searched
     * @param matcher the matcher to apply for filtering members
     * @return a list of members that match the given conditions
     */
    @Override
    public List<T> find(Class<?> clazz, Matcher<? super T> matcher) {
        Matcher<Member> declaringClass = MemberMatcher.isDeclaredClass(clazz);
        Collection<T>   members        = getMembers(clazz, true);
        List<T>         matched        = members.stream()
                .filter(declaringClass::matches).filter(matcher::matches).toList();

        if (matched.size() == 0) {
            matched = members.stream().filter(matcher::matches).toList();
        }

        return matched;
    }

}