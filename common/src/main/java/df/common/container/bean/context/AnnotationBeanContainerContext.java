package df.common.container.bean.context;

import df.common.container.bean.BeanFacrotyBuilder;
import df.common.container.bean.processor.Processable;
import df.common.container.bean.BeanFactory;
import df.common.container.bean.processor.BeanProcessor;

public class AnnotationBeanContainerContext implements BeanContainerContext, Processable {

    private final BeanFactory factory;

    private AnnotationBeanContainerContext(BeanFactory factory) {
        this.factory = factory;
        this.factory.setApplicationContext(this);
    }

    public static BeanContainerContext run(Class<?>... classes) {
        Class<?>[]  root    = classes == null ? new Class<?>[]{BeanContainerContext.class} : classes;
        BeanFactory factory = new BeanFacrotyBuilder(root).build();

        return new AnnotationBeanContainerContext(factory);
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        return factory.getBean(beanType);
    }

    @Override
    public <T> T getBean(String beanName) {
        return factory.getBean(beanName);
    }

    @Override
    public <T> T getBean(Class<T> beanType, String beanName) {
        return factory.getBean(beanType, beanName);
    }

    @Override
    public void addBeanProcessor(BeanProcessor processor) {
        factory.addBeanProcessor(processor);
    }

    @Override
    public BeanFactory getFactory() {
        return factory;
    }
}
