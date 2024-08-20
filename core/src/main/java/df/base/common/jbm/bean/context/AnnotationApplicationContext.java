package df.base.common.jbm.bean.context;

import df.base.common.jbm.bean.BeanFacrotyBuilder;
import df.base.common.jbm.bean.BeanFactory;
import df.base.common.jbm.bean.processor.BeanProcessor;
import df.base.common.jbm.bean.processor.Processable;

public class AnnotationApplicationContext implements JbmContext, Processable {

    private final BeanFactory factory;

    private AnnotationApplicationContext(BeanFactory factory) {
        this.factory = factory;
        this.factory.setApplicationContext(this);
    }

    public static JbmContext run(Class<?>... classes) {
        Class<?>[]  root    = classes == null ? new Class<?>[]{JbmContext.class} : classes;
        BeanFactory factory = new BeanFacrotyBuilder(root).build();

        return new AnnotationApplicationContext(factory);
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
