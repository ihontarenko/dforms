package df.base.common.application_context.bean.context;

import df.base.common.application_context.bean.BeanFactory;
import df.base.common.application_context.bean.processor.Processable;

public interface ApplicationContext extends Processable {

    <T> T getBean(Class<T> beanType, String beanName);

    <T> T getBean(Class<T> beanType);

    <T> T getBean(String beanName);

    BeanFactory getFactory();

}
