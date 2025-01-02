package df.base.common.container.bean.context;

import df.base.common.container.bean.BeanFacrotyBuilder;
import df.base.common.container.bean.processor.Processable;
import df.base.common.container.bean.BeanFactory;
import df.base.common.container.bean.processor.BeanProcessor;

public class AnnotationJbmContext implements BeanContainerContext, Processable {

    private final BeanFactory factory;

    private AnnotationJbmContext(BeanFactory factory) {
        this.factory = factory;
        this.factory.setApplicationContext(this);
    }

    public static BeanContainerContext run(Class<?>... classes) {
        Class<?>[]  root    = classes == null ? new Class<?>[]{BeanContainerContext.class} : classes;
        BeanFactory factory = new BeanFacrotyBuilder(root).build();

        return new AnnotationJbmContext(factory);
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
