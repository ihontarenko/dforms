package df.base.common.matcher;

import java.util.Collection;

public class CollectionMatchers {

    public static <T> Matcher<Collection<T>> contains(T element) {
        return new CollectionContainsMatcher<>(element);
    }

    public static <T> Matcher<Collection<T>> isEmpty() {
        return new CollectionEmptyMatcher<>();
    }

    public static <T> Matcher<Collection<T>> size(int size) {
        return new CollectionSizeMatcher<>(size);
    }

    public static <T> Matcher<Collection<T>> anyOf(Matcher<? super T> itemMatcher) {
        return new CollectionAnyOfMatcher<>(itemMatcher);
    }

    static class CollectionAnyOfMatcher<T> implements Matcher<Collection<T>> {

        private final Matcher<? super T> itemMatcher;

        public CollectionAnyOfMatcher(Matcher<? super T> itemMatcher) {
            this.itemMatcher = itemMatcher;
        }

        @Override
        public boolean matches(Collection<T> collection, MatchContext context) {
            for (T item : collection) {
                if (itemMatcher.matches(item, context)) {
                    return true;
                }
            }
            return false;
        }
    }

    static class CollectionContainsMatcher<T> implements Matcher<Collection<T>> {

        private final T element;

        public CollectionContainsMatcher(T element) {
            this.element = element;
        }

        @Override
        public boolean matches(Collection<T> collection, MatchContext context) {
            return collection != null && collection.contains(element);
        }

    }

    static class CollectionEmptyMatcher<T> implements Matcher<Collection<T>> {
        @Override
        public boolean matches(Collection<T> collection, MatchContext context) {
            return collection != null && collection.isEmpty();
        }
    }

    static class CollectionSizeMatcher<T> implements Matcher<Collection<T>> {

        private final int expectedSize;

        public CollectionSizeMatcher(int expectedSize) {
            this.expectedSize = expectedSize;
        }

        @Override
        public boolean matches(Collection<T> collection, MatchContext context) {
            return collection != null && collection.size() == expectedSize;
        }

    }

}
