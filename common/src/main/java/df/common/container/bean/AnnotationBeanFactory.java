package df.common.container.bean;

import df.common.container.ClassUtils;
import df.common.container.bean.annotation.BeanConstructor;
import df.common.container.bean.annotation.Lifecycle;
import df.common.container.bean.annotation.Lifecycle.Scope;
import df.common.container.bean.annotation.Name;
import df.common.container.bean.annotation.Provide;
import df.common.container.bean.context.BeanContainerContext;
import df.common.container.bean.creation.BeanCreationStrategy;
import df.common.container.bean.creation.ConstructorBeanCreationStrategy;
import df.common.container.bean.creation.MethodBeanCreationStrategy;
import df.common.container.bean.creation.SupplierBeanCreationStrategy;
import df.common.container.bean.definition.*;
import df.common.container.bean.processor.BeanProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static df.common.container.ReflectionUtils.findFirstAnnotatedConstructor;
import static df.common.container.ReflectionUtils.findFirstConstructor;
import static df.common.container.StringUtils.underscored;
import static java.util.stream.Collectors.joining;

public class AnnotationBeanFactory implements BeanFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanFactory.class);

    private final Map<String, BeanDefinition> definitions;
    private final Map<String, Object>         beans;
    private final Map<Class<?>, List<String>>  names;
    private final BeanCreationStrategyResolver resolver;
    private final List<BeanProcessor>          processors = new ArrayList<>();
    private final List<BeanDefinition>        visitor;
    private       BeanContainerContext        context;

    public AnnotationBeanFactory() {
        this.beans = new ConcurrentHashMap<>();
        this.definitions = new ConcurrentHashMap<>();
        this.names = new ConcurrentHashMap<>();
        this.resolver = new BeanCreationStrategyResolver(
                new ConstructorBeanCreationStrategy(), new MethodBeanCreationStrategy(), new SupplierBeanCreationStrategy());
        this.visitor = new ArrayList<>();
    }

    @Override
    public <T> T getBean(Class<T> beanType, String beanName) {
        String finalName = beanName;

        if (finalName == null) {
            finalName = resolveBeanName(beanType, getBeanName(beanType));
        }

        return getBean(finalName);
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        return getBean(beanType, null);
    }

    @SuppressWarnings({"all"})
    @Override
    public <T> T getBean(String name) {
        T bean = (T) beans.get(name);

        if (bean == null) {
            BeanDefinition definition = getBeanDefinition(name);

            // check if requested definition currently in progress
            if (visitor.contains(definition)) {
                visitor.add(definition);
                String dependencyPath = visitor.stream().map(BeanDefinition::getBeanName)
                        .collect(joining("\n\t -> "));
                visitor.clear();
                throw new ObjectCreationException(
                        "CYCLIC DEPENDENCIES DETECTED DURING BEAN CREATION. DEPENDENCIES CHAIN: [\n\t -> %s\n]"
                                .formatted(dependencyPath));
            }

            // if not add it for visitor
            visitor.add(definition);

            bean = createBean(definition);

            if (definition.isSingleton()) {
                beans.put(definition.getBeanName(), bean);
            }

            // remove definition from stack after successful creation
            visitor.remove(definition);
        }

        return bean;
    }

    @Override
    public String resolveBeanName(Class<?> type, String likelyName) {
        String       resolvedName = likelyName;
        List<String> names        = getBeanNames(type);

        if (names.size() == 1) {
            resolvedName = names.get(0);
        } else if (names.size() > 1 && !names.contains(resolvedName)) {
            throw new ObjectCreationException(
                    "SPECIFY THE ACTUAL BEAN NAME OF ONE OF %s NAMES FOR TYPE: '%s'"
                            .formatted(names, type));
        } else if (names.isEmpty()) {
            throw new ObjectCreationException(
                    "NO BEAN NAMES FOR TYPE: '%s'".formatted(type));
        }

        return resolvedName;
    }

    @Override
    public List<String> getBeanNames(Class<?> type) {
        return names.computeIfAbsent(type, k -> new ArrayList<>());
    }

    @Override
    public <T> T createBean(BeanDefinition definition) {
        BeanCreationStrategy strategy = resolver.resolve(definition);
        T                    instance = (T) strategy.createBean(definition, this);

        if (instance == null) {
            throw new ObjectCreationException(
                    "UNFORTUNATELY, THE STRATEGY FAILED TO CREATE THE BEAN OF TYPE: " + definition.getBeanClass());
        }

        LOGGER.info("CREATED: BEAN [%s] STRATEGY [%s]"
                .formatted(definition.getBeanName(), underscored(ClassUtils.getShortName(strategy.getClass()), true)));

        // pass bean for processors
        for (BeanProcessor processor : processors) {
            processor.process(instance, getApplicationContext());
        }

        // assign bean instance for it definition
        definition.setBeanInstance(instance);

        return instance;
    }

    @Override
    public BeanDefinition getBeanDefinition(Class<?> type) {
        return getBeanDefinition(getBeanName(type));
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        BeanDefinition definition = definitions.get(name);

        if (definition == null) {
            throw new ObjectCreationException("NO BEAN DEFINITION FOUND FOR NAME: " + name);
        }

        return definition;
    }

    @Override
    public BeanDefinition createBeanDefinition(Class<?> interfaceType, List<Class<?>> subClasses) {
        String                beanName   = getBeanName(interfaceType);
        DefaultBeanDefinition definition = new DefaultBeanDefinition(beanName, interfaceType);

        // interface cannot be a true bean
        definition.setBeanScope(Scope.NON_BEAN);

        for (Class<?> subClass : subClasses) {
            BeanDefinition subClassDefinition = createBeanDefinition(subClass);
            subClassDefinition.setParentDefinition(definition);
            definition.addChildDefinition(subClassDefinition);
            registerBeanDefinition(subClassDefinition);
        }

        return definition;
    }

    @Override
    public BeanDefinition createBeanDefinition(Class<?> klass) {
        String                    beanName   = getBeanName(klass);
        ConstructorBeanDefinition definition = new ConstructorBeanDefinition(beanName, klass);

        // registerBeanDefinition(definition);

        Constructor<?> constructor;

        try {
            constructor = findFirstAnnotatedConstructor(klass, BeanConstructor.class);
        } catch (Exception annotatedConstructorException) {
            try {
                constructor = findFirstConstructor(klass);
            } catch (Exception defaultConstructorException) {
                RuntimeException exception = new ObjectCreationException(
                        "NO CONSTRUCTOR WAS FOUND. PLEASE CREATE A DEFAULT CONSTRUCTOR FOR (" + klass + ") AT LEAST");
                defaultConstructorException.addSuppressed(annotatedConstructorException);
                exception.addSuppressed(defaultConstructorException);
                throw exception;
            }
        }

        definition.setConstructor(constructor);

        if (constructor.getParameterCount() != 0) {
            for (Parameter parameter : constructor.getParameters()) {
                processDependencies(definition.getBeanDependencies(), parameter);
            }
        }

        if (klass.isAnnotationPresent(Provide.class)) {
            definition.setBeanScope(klass.getAnnotation(Provide.class).scope());
        } else if (klass.isAnnotationPresent(Lifecycle.class)) {
            definition.setBeanScope(klass.getAnnotation(Lifecycle.class).value());
        } else {
            definition.setBeanScope(Scope.SINGLETON);
        }

        return definition;
    }

    @Override
    public BeanDefinition createBeanDefinition(Class<?> type, Class<?> klass, Method factoryMethod) {
        String               beanName   = getBeanName(factoryMethod);
        MethodBeanDefinition definition = new MethodBeanDefinition(beanName, factoryMethod.getReturnType());

        definition.setBeanFactoryMethod(factoryMethod);

        if (factoryMethod.getParameterCount() != 0) {
            for (Parameter parameter : factoryMethod.getParameters()) {
                processDependencies(definition.getBeanDependencies(), parameter);
            }
        }

        if (factoryMethod.isAnnotationPresent(Provide.class)) {
            Provide annotation = factoryMethod.getAnnotation(Provide.class);

            definition.setBeanScope(annotation.scope());

            if (!annotation.lazy()) {
                createBean(definition);
            }
        } else if (factoryMethod.isAnnotationPresent(Lifecycle.class)) {
            definition.setBeanScope(factoryMethod.getAnnotation(Lifecycle.class).value());
        } else {
            definition.setBeanScope(Scope.SINGLETON);
        }

        return definition;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition definition) {
        String beanName = definition.getBeanName();

        if (definitions.containsKey(beanName)) {
            throw new DuplicateBeanDefinitionException(definition);
        }

        definitions.put(beanName, definition);
        updateBeanNames(definition);
    }

    @Override
    public void addBeanProcessor(BeanProcessor processor) {
        this.processors.add(processor);
    }

    @Override
    public BeanContainerContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(BeanContainerContext context) {
        this.context = context;
    }

    private void updateBeanNames(BeanDefinition definition) {
        List<String>   beanNames        = getBeanNames(definition.getBeanClass());
        String         beanName         = definition.getBeanName();
        BeanDefinition parentDefinition = definition.getParentDefinition();

        beanNames.add(beanName);

        if (parentDefinition != null) {
            beanNames.add(parentDefinition.getBeanName());
            getBeanNames(parentDefinition.getBeanClass()).add(beanName);
        }
    }

    private void processDependencies(List<BeanDependency> dependencies, Parameter parameter) {
        String name = null;

        if (parameter.isAnnotationPresent(Name.class)) {
            name = parameter.getAnnotation(Name.class).value();
        }

        dependencies.add(new NamedDependency(parameter.getType(), name));
    }

}