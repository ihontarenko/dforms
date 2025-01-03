package df.common.container.bean.context;

import df.common.container.bean.processor.Processable;
import df.common.container.bean.BeanFactory;

public interface BeanContainerContext extends Processable {

    <T> T getBean(Class<T> beanType, String beanName);

    <T> T getBean(Class<T> beanType);

    <T> T getBean(String beanName);

    BeanFactory getFactory();

}
