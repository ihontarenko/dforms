package df.application.provider.bean;

import df.application.SpringApplicationContext;
import org.jmouse.core.context.beans.BeanLookup;
import org.springframework.context.ApplicationContext;

public class SpringBeanLookup implements BeanLookup {

    private final ApplicationContext context;

    public SpringBeanLookup() {
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
