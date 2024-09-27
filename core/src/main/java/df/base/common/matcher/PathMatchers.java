package df.base.common.matcher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class PathMatchers {

    public static Matcher<Path> exists() {
        return new PathExistsMatcher();
    }

    public static Matcher<Path> isDirectory() {
        return new PathIsDirectoryMatcher();
    }

    public static Matcher<Path> isFile() {
        return new PathIsFileMatcher();
    }

    public static Matcher<Path> hasExtension(String extension) {
        return new PathHasExtensionMatcher(extension);
    }

    public static Matcher<Path> isReadable() {
        return new PathIsReadableMatcher();
    }

    public static Matcher<Path> isWritable() {
        return new PathIsWritableMatcher();
    }

    public static Matcher<Path> size(long expectedSize) {
        return new PathSizeMatcher(expectedSize);
    }

    public static Matcher<Path> isExecutable() {
        return new PathIsExecutableMatcher();
    }

    public static Matcher<Path> matchesPattern(String regex) {
        return new PathMatchesPatternMatcher(regex);
    }

    private static class PathExistsMatcher implements Matcher<Path> {
        @Override
        public  boolean matches(Path path, MatchContext context) {
            return Files.exists(path);
        }
    }

    private static class PathIsDirectoryMatcher implements Matcher<Path> {
        @Override
        public  boolean matches(Path path, MatchContext context) {
            return Files.isDirectory(path);
        }
    }

    private static class PathIsFileMatcher implements Matcher<Path> {
        @Override
        public  boolean matches(Path path, MatchContext context) {
            return Files.isRegularFile(path);
        }
    }

    private record PathHasExtensionMatcher(String extension) implements Matcher<Path> {
        @Override
        public boolean matches(Path path, MatchContext context) {
            String fileName = path.getFileName().toString();
            return fileName.endsWith("." + extension);
        }
    }

    private static class PathIsReadableMatcher implements Matcher<Path> {
        @Override
        public  boolean matches(Path path, MatchContext context) {
            return Files.isReadable(path);
        }
    }

    private static class PathIsWritableMatcher implements Matcher<Path> {
        @Override
        public  boolean matches(Path path, MatchContext context) {
            return Files.isWritable(path);
        }
    }

    private record PathSizeMatcher(long expectedSize) implements Matcher<Path> {
        @Override
        public boolean matches(Path path, MatchContext context) {
            try {
                return Files.size(path) == expectedSize;
            } catch (Exception e) {
                return false;
            }
        }
    }

    private static class PathIsExecutableMatcher implements Matcher<Path> {
        @Override
        public  boolean matches(Path path, MatchContext context) {
            return Files.isExecutable(path);
        }
    }

    private static class PathMatchesPatternMatcher implements Matcher<Path> {
        private final Pattern pattern;

        public PathMatchesPatternMatcher(String regex) {
            this.pattern = Pattern.compile(regex);
        }

        @Override
        public  boolean matches(Path path, MatchContext context) {
            String fileName = path.getFileName().toString();
            return pattern.matcher(fileName).matches();
        }
    }
}

