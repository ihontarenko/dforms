package df.base.service;

import df.base.common.libs.jbm.scanner.ClassScanner;
import df.base.common.matcher.Matcher;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toSet;

@Service
public class ClassRepository {

    private static final ClassScanner                SCANNER = ClassScanner.getDefaultScanner();
    private static final Map<Integer, Set<Class<?>>> CLASSES = new HashMap<>();

    private final Class<?>[] baseClasses;

    public ClassRepository(Class<?>... baseClasses) {
        this.baseClasses = baseClasses;
    }

    public Set<Class<?>> findClasses(Matcher<Class<?>> matcher) {
        return getAllClasses(baseClasses).stream().filter(matcher::matches).collect(toSet());
    }

    @SuppressWarnings({"all"})
    public static Set<Class<?>> getAllClasses(Class<?>... baseClasses) {
        int cacheKey = Objects.hash(baseClasses);

        if (!CLASSES.containsKey(cacheKey)) {
            Set<Class<?>> classes = new HashSet<>();

            for (Class<?> baseClass : baseClasses) {
                classes.addAll(SCANNER.scan(baseClass.getPackageName(), baseClass.getClassLoader()));
            }

            CLASSES.put(cacheKey, classes);
        }

        return CLASSES.get(cacheKey);
    }

}
