package df.base.common.jbm.bean.creation;

import df.base.common.jbm.bean.BeanDependency;
import df.base.common.jbm.bean.BeanFactory;
import df.base.common.jbm.bean.ObjectCreationException;
import df.base.common.jbm.bean.definition.BeanDefinition;
import df.base.common.jbm.bean.definition.ConstructorBeanDefinition;

import java.util.List;

import static df.base.common.jbm.ReflectionUtils.instantiate;

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

        return instantiate(beanDefinition.getConstructor(), arguments);
    }

    @Override
    public boolean canCreate(BeanDefinition definition) {
        return ConstructorBeanDefinition.class.isAssignableFrom(definition.getClass());
    }
}
