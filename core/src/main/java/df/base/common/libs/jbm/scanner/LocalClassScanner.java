package df.base.common.libs.jbm.scanner;

import df.base.common.libs.jbm.scanner.filter.path.IsFileExtensionFilter;
import df.base.common.libs.jbm.scanner.filter.path.IsRegularPathFilter;
import df.base.common.matcher.PathMatchers;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static df.base.common.matcher.PathMatchers.hasExtension;
import static df.base.common.matcher.PathMatchers.isFile;

public class LocalClassScanner extends AbstractScanner<Class<?>> {

    private final FileScanner scanner = new FileScanner() {{
        addFilter(new IsRegularPathFilter());
        addFilter(new IsFileExtensionFilter("class"));
        PathMatchers.exists()
                .and(isFile()).and(hasExtension("class"));
    }};

    @Override
    public Set<Class<?>> scan(URL resource, String name, ClassLoader loader) {
        Set<Class<?>> classes = new HashSet<>();

        for (Path path : scanner.scan(resource, name, loader)) {
            try {
                String packagePath = path.toFile().getParent().replaceAll("[\\\\/]+", ".");
                int    lastIndex   = packagePath.lastIndexOf(name);
                String packageName = packagePath.substring(lastIndex);

                classes.add(loader.loadClass(getClassName(packageName, path.toFile())));
            } catch (Throwable ignore) { }
        }

        return classes;
    }

    @Override
    public boolean supports(Object object) {
        return object.equals("file");
    }

    private String getClassName(String name, File file) {
        return name + '.' + file.getName().substring(0, file.getName().length() - CLASS_FILE_SUFFIX.length());
    }


}
