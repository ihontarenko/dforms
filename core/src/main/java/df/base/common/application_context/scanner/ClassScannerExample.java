package df.base.common.application_context.scanner;

import df.base.common.application_context.filter.FilteringMode;
import df.base.common.application_context.scanner.filter.path.IsRegularPathFilter;
import df.base.common.application_context.scanner.filter.type.AccessModifierClassFilter;

import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class ClassScannerExample {

    public static void main(String... arguments) {
        ClassLoader loader = ClassLoader.getSystemClassLoader();

        ClassScanner        scanner          = new ClassScanner();
        JrtClassScanner       jrtClassScanner     = new JrtClassScanner("java.base");
        ResourcesClassScanner defaultClassScanner = new ResourcesClassScanner();

        defaultClassScanner.addScanner(new LocalClassScanner());
        defaultClassScanner.addScanner(new JarClassScanner());

        scanner.addScanner(jrtClassScanner);
        scanner.addScanner(defaultClassScanner);

//        scanner.addIncludeFilter(Class::isEnum);
//        scanner.addFilter(Class::isInterface);
        scanner.addFilter(Class::isAnnotation);
//        scanner.addIncludeFilter(new AnnotationClassFilter(FunctionalInterface.class));
//        scanner.addIncludeFilter(new SubclassClassFilter(Serializable.class));
//        scanner.addFilter(new SubclassClassFilter(BeanDefinition.class));
//        scanner.addFilter(new AccessModifierClassFilter(Modifier.ABSTRACT, false));

        scanner.setFilteringMode(FilteringMode.OR);

//        Set<Class<?>> classes = scanner.scan("df.base.common.application_context", loader);
        Set<Class<?>> classes = scanner.scan("df.base", loader);

        for (Class<?> type : classes) {
            System.out.println(type);
        }

        System.out.println("total classes found: " + classes.size());
/*
        FileScanner filesScanner = new FileScanner();

        filesScanner.addFilter(new IsRegularPathFilter());

        System.out.println(
                ClassLoader.getSystemClassLoader().getResource("db/")
        );

        Set<Path> paths = filesScanner.scan("db/", ClassLoader.getSystemClassLoader());

        for (Path path : paths) {
            System.out.println(path);
        }*/
    }

}