package df.base.common.jbm.bean.context;

import df.base.common.jbm.bean.BeanFactory;
import df.base.common.jbm.bean.processor.Processable;

public interface JbmContext extends Processable {

    <T> T getBean(Class<T> beanType, String beanName);

    <T> T getBean(Class<T> beanType);

    <T> T getBean(String beanName);

    BeanFactory getFactory();

}
