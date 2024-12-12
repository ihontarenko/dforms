package df.base.common.context;

public interface BeanProviderAware {
    void setBeanProvider(BeanProvider beanProvider);
    BeanProvider getBeanProvider();
}
