package df.common.provider.bean;

import df.common.extensions.spring.context.SpringApplicationContext;
import org.springframework.context.ApplicationContext;
import svit.support.context.BeanProvider;

public class SpringBeanProvider implements BeanProvider {

    private final ApplicationContext context;

    public SpringBeanProvider() {
        context = SpringApplicationContext.getApplicationContext();
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanClass) {
        return context.getBean(beanName, beanClass);
    }

}
