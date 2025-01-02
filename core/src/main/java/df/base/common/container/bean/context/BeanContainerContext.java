package df.base.common.container.bean.context;

import df.base.common.container.bean.processor.Processable;
import df.base.common.container.bean.BeanFactory;

public interface BeanContainerContext extends Processable {

    <T> T getBean(Class<T> beanType, String beanName);

    <T> T getBean(Class<T> beanType);

    <T> T getBean(String beanName);

    BeanFactory getFactory();

}
