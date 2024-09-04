package df.base.common.libs.jbm.bean.context;

import df.base.common.libs.jbm.bean.BeanFacrotyBuilder;
import df.base.common.libs.jbm.bean.BeanFactory;
import df.base.common.libs.jbm.bean.processor.BeanProcessor;
import df.base.common.libs.jbm.bean.processor.Processable;

public class AnnotationJbmContext implements JbmContext, Processable {

    private final BeanFactory factory;

    private AnnotationJbmContext(BeanFactory factory) {
        this.factory = factory;
        this.factory.setApplicationContext(this);
    }

    public static JbmContext run(Class<?>... classes) {
        Class<?>[]  root    = classes == null ? new Class<?>[]{JbmContext.class} : classes;
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
