package df.base.common.context.provider.bean;

import df.base.common.context.BeanProvider;
import df.base.common.container.bean.context.JbmContext;

public class JbmBeanProvider implements BeanProvider {

    private final JbmContext jbmContext;

    public JbmBeanProvider(JbmContext jbmContext) {
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
