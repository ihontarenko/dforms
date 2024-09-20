package df.base.common.context;

import df.base.common.libs.jbm.bean.context.JbmContext;

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
