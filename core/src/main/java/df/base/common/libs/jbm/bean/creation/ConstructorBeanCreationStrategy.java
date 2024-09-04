package df.base.common.libs.jbm.bean.creation;

import df.base.common.libs.jbm.bean.BeanDependency;
import df.base.common.libs.jbm.bean.BeanFactory;
import df.base.common.libs.jbm.bean.ObjectCreationException;
import df.base.common.libs.jbm.bean.definition.BeanDefinition;
import df.base.common.libs.jbm.bean.definition.ConstructorBeanDefinition;
import df.base.common.libs.jbm.ReflectionUtils;

import java.util.List;

public class ConstructorBeanCreationStrategy extends AbstractBeanCreationStrategy {

    @Override
    public Object createBean(BeanDefinition definition, BeanFactory factory) {
        ConstructorBeanDefinition beanDefinition = (ConstructorBeanDefinition) definition;
        List<BeanDependency>      dependencies   = beanDefinition.getBeanDependencies();
        Object[]                  arguments      = new Object[0];

        if (!dependencies.isEmpty()) {
            try {
                arguments = getArguments(dependencies, factory);
            } catch (RuntimeException exception) {
                throw new ObjectCreationException(
                        "UNABLE TO CREATE BEAN VIA CONSTRUCTOR STRATEGY FOR BEAN TYPE: " + definition.getBeanClass(), exception);
            }
        }

        return ReflectionUtils.instantiate(beanDefinition.getConstructor(), arguments);
    }

    @Override
    public boolean canCreate(BeanDefinition definition) {
        return ConstructorBeanDefinition.class.isAssignableFrom(definition.getClass());
    }
}
