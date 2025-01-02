package df.base.common.context.provider.bean;

import df.base.common.context.BeanProvider;
import df.base.common.container.bean.context.BeanContainerContext;

public class JbmBeanProvider implements BeanProvider {

    private final BeanContainerContext jbmContext;

    public JbmBeanProvider(BeanContainerContext jbmContext) {
        this.jbmContext = jbmContext;
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        return jbmContext.getBean(beanClass);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanClass) {
        return jbmContext.getBean(beanClass, beanName);
    }

}
