package df.base.internal.jbm.bean.context;

import df.base.internal.jbm.bean.BeanFactory;
import df.base.internal.jbm.bean.processor.Processable;

public interface JbmContext extends Processable {

    <T> T getBean(Class<T> beanType, String beanName);

    <T> T getBean(Class<T> beanType);

    <T> T getBean(String beanName);

    BeanFactory getFactory();

}
