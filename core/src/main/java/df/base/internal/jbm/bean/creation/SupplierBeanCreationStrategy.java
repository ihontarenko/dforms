package df.base.internal.jbm.bean.creation;

import df.base.internal.jbm.bean.BeanFactory;
import df.base.internal.jbm.bean.definition.BeanDefinition;
import df.base.internal.jbm.bean.definition.SupplierBeanDefinition;

public class SupplierBeanCreationStrategy extends AbstractBeanCreationStrategy {

    @Override
    public Object createBean(BeanDefinition definition, BeanFactory factory) {
        return ((SupplierBeanDefinition) definition).getSupplier().get();
    }

    @Override
    public boolean canCreate(BeanDefinition definition) {
        return SupplierBeanDefinition.class.isAssignableFrom(definition.getClass());
    }
}
