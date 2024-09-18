package df.base.common.pipeline;

public interface BeanProvider {

    <T> T getBean(Class<T> beanClass);

    <T> T getBean(String beanName, Class<T> beanClass);

}
