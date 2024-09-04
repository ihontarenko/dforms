package df.base.common.jbm.bean.definition;

import df.base.common.jbm.bean.BeanCreationType;

public class DefaultBeanDefinition extends AbstractBeanDefinition {

    public DefaultBeanDefinition(String name, Class<?> type) {
        super(name, type);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("DEFAULT_BEAN_DEFINITION: ");

        builder.append(super.toString());

        return builder.toString();
    }

    @Override
    public BeanCreationType getBeanCreationType() {
        return BeanCreationType.SUB_CLASSES;
    }
}
