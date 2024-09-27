package df.base.common.matcher;

public class TextMatchers {

    public static Matcher<String> contains(String substring) {
        return new TextContainsMatcher(substring);
    }

    public static Matcher<String> same(String substring) {
        return new TextEqualsMatcher(substring);
    }

    public static Matcher<String> startsWith(String prefix) {
        return new TextStartsWithMatcher(prefix);
    }

    public static Matcher<String> endsWith(String suffix) {
        return new TextEndsWithMatcher(suffix);
    }

    private record TextEqualsMatcher(String string) implements Matcher<String> {
        @Override
        public boolean matches(String item, MatchContext context) {
            return item != null && item.equals(string);
        }
    }

    private record TextContainsMatcher(String substring) implements Matcher<String> {
        @Override
        public boolean matches(String item, MatchContext context) {
            return item != null && item.contains(substring);
        }
    }

    private record TextStartsWithMatcher(String prefix) implements Matcher<String> {
        @Override
        public boolean matches(String item, MatchContext context) {
            return item != null && item.startsWith(prefix);
        }
    }

    private record TextEndsWithMatcher(String suffix) implements Matcher<String> {
        @Override
        public boolean matches(String item, MatchContext context) {
            return item != null && item.endsWith(suffix);
        }
    }

}


