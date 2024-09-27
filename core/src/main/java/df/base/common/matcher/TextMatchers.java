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

    static class TextEqualsMatcher implements Matcher<String> {
        private final String string;

        public TextEqualsMatcher(String string) {
            this.string = string;
        }

        @Override
        public boolean matches(String item, MatchContext context) {
            return item != null && item.equals(string);
        }
    }

    static class TextContainsMatcher implements Matcher<String> {
        private final String substring;

        public TextContainsMatcher(String substring) {
            this.substring = substring;
        }

        @Override
        public boolean matches(String item, MatchContext context) {
            return item != null && item.contains(substring);
        }
    }

    static class TextStartsWithMatcher implements Matcher<String> {
        private final String prefix;

        public TextStartsWithMatcher(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public boolean matches(String item, MatchContext context) {
            return item != null && item.startsWith(prefix);
        }
    }

    static class TextEndsWithMatcher implements Matcher<String> {
        private final String suffix;

        public TextEndsWithMatcher(String suffix) {
            this.suffix = suffix;
        }

        @Override
        public boolean matches(String item, MatchContext context) {
            return item != null && item.endsWith(suffix);
        }
    }

}


