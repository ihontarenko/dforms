package df.parent.library.confio;

import org.springframework.context.ApplicationContext;

public class SpringBoot3ComponentResolver implements ComponentResolver<ApplicationContext>{

    private ApplicationContext resolver;

    @Override
    public void configure(ApplicationContext resolver) {
        this.resolver = resolver;
    }

    @Override
    public Object resolve(String name) {
        return resolver.getBean(getBeanClassType(name));
    }

    private Class<?> getBeanClassType(final String name) {
        String   className = name;
        Class<?> classType;

        if (className.indexOf('/') != -1) {
            className = className.replace('/', '.');
        }

        if (className.indexOf('.') == 0) {
            className = className.substring(1);
        }

        try {
            classType = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ConfioException("Unable to resolve Class for name: %s".formatted(className), e);
        }

        return classType;
    }

}
