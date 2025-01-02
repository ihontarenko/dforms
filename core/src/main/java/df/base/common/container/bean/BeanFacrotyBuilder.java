package df.base.common.container.bean;

import df.base.common.container.Builder;
import df.base.common.matcher.reflection.TypeMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static df.base.common.container.ReflectionUtils.findAllAnnotatedMethods;
import static df.base.common.container.bean.BeanClassScanner.*;
import static df.base.common.container.bean.BeanClassScanner.METHOD_BEAN_ANNOTATED_CLASS_FILTER;

public class BeanFacrotyBuilder implements Builder<BeanFactory> {

    private static final Logger     LOGGER = LoggerFactory.getLogger(BeanFacrotyBuilder.class);
    private final        Class<?>[] classes;

    public BeanFacrotyBuilder(Class<?>[] classes) {
        this.classes = classes;
    }

    @Override
    public BeanFactory build() {
        BeanFactory factory = new AnnotationBeanFactory();

        // scanning classes @Bean annotated
        Set<Class<?>> candidates = scanBeanClasses(classes);

        LOGGER.info("BEAN CANDIDATES FOUND: %d".formatted(candidates.size()));

        for (Class<?> candidate : candidates) {

            LOGGER.debug("CURRENT CANDIDATE: %s".formatted(candidate.getName()));

            // is bean interface founded
            if (candidate.isInterface()) {

                LOGGER.info("A CANDIDATE BEAN IS AN INTERFACE. %s".formatted(candidate.getCanonicalName()));

                // find all classes implemented current interface
                List<Class<?>> subClasses = new ArrayList<>();
                for (Class<?> subClass : scanPackagesWithFilter(TypeMatchers.isSupertype(candidate), classes)) {
                    if (!candidates.contains(subClass)) {
                        subClasses.add(subClass);
                    }
                }

                LOGGER.info("CHILD CLASSES: %d".formatted(subClasses.size()));

                // no registration for this definition
                factory.createBeanDefinition(candidate, subClasses);
            } else {
                factory.registerBeanDefinition(
                        factory.createBeanDefinition(candidate)
                );
            }
        }

        // scanning bean factory methods
        for (Class<?> candidate : scanBeanConfigurationClasses(classes)) {
            for (Method method : findAllAnnotatedMethods(candidate, Bean.class)) {
                factory.registerBeanDefinition(
                        factory.createBeanDefinition(method.getReturnType(), method.getDeclaringClass(), method)
                );
            }
        }

        return factory;
    }



    private static Set<Class<?>> scanBeanClasses(Class<?>... classes) {
        return scanPackagesWithFilter(CLASS_BEAN_ANNOTATED_CLASS_FILTER, classes);
    }

    private static Set<Class<?>> scanBeanConfigurationClasses(Class<?>... classes) {
        Set<Class<?>> candidates = new HashSet<>();

        candidates.addAll(scanPackagesWithFilter(CLASS_CONFIGURATION_ANNOTATED_CLASS_FILTER, classes));
        candidates.addAll(scanPackagesWithFilter(METHOD_BEAN_ANNOTATED_CLASS_FILTER, classes));

        return candidates;
    }

}
