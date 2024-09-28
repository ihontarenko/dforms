package df.base.common.matcher;

@SuppressWarnings({"unused"})
public interface Matcher<T> {

    boolean matches(T item, MatchContext context);

    default Matcher<T> and(Matcher<? super T> other) {
        return new AndMatcher<>(this, other);
    }

    default Matcher<T> or(Matcher<? super T> other) {
        return new OrMatcher<>(this, other);
    }

    default Matcher<T> xor(Matcher<? super T> other) {
        return new XorMatcher<>(this, other);
    }

    default Matcher<T> not() {
        return new NotMatcher<>(this);
    }

    static <T> Matcher<T> and(Matcher<? super T> first, Matcher<? super T> second) {
        return new AndMatcher<>(first, second);
    }

    static <T> Matcher<T> or(Matcher<? super T> first, Matcher<? super T> second) {
        return new OrMatcher<>(first, second);
    }

    static <T> Matcher<T> xor(Matcher<? super T> first, Matcher<? super T> second) {
        return new XorMatcher<>(first, second);
    }

    static <T> Matcher<T> not(Matcher<? super T> matcher) {
        return new NotMatcher<>(matcher);
    }

    static <T> Matcher<T> empty(boolean value) {
        return (item, context) -> value;
    }

    class AndMatcher<T> implements Matcher<T> {

        private final Matcher<? super T> left;
        private final Matcher<? super T> right;

        public AndMatcher(Matcher<? super T> left, Matcher<? super T> right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean matches(T item, MatchContext context) {
            return left.matches(item, context) && right.matches(item, context);
        }

        @Override
        public String toString() {
            return "AND";
        }
    }

    class OrMatcher<T> implements Matcher<T> {

        private final Matcher<? super T> left;
        private final Matcher<? super T> right;

        public OrMatcher(Matcher<? super T> left, Matcher<? super T> right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean matches(T item, MatchContext context) {
            return left.matches(item, context) || right.matches(item, context);
        }

        @Override
        public String toString() {
            return "OR";
        }

    }

    class XorMatcher<T> implements Matcher<T> {

        private final Matcher<? super T> left;
        private final Matcher<? super T> right;

        public XorMatcher(Matcher<? super T> left, Matcher<? super T> right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean matches(T item, MatchContext context) {
            return left.matches(item, context) ^ right.matches(item, context);
        }

        @Override
        public String toString() {
            return "XOR";
        }

    }

    class NotMatcher<T> implements Matcher<T> {

        private final Matcher<? super T> matcher;

        public NotMatcher(Matcher<? super T> matcher) {
            this.matcher = matcher;
        }

        @Override
        public boolean matches(T item, MatchContext context) {
            return !matcher.matches(item, context);
        }

        @Override
        public String toString() {
            return "NOT";
        }

    }

}
