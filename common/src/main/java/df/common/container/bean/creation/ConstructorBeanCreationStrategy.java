package df.common.container.bean.creation;

import df.common.container.ReflectionUtils;
import df.common.container.bean.BeanDependency;
import df.common.container.bean.BeanFactory;
import df.common.container.bean.ObjectCreationException;
import df.common.container.bean.definition.ConstructorBeanDefinition;
import df.common.container.bean.definition.BeanDefinition;

import java.util.List;

public class ConstructorBeanCreationStrategy extends AbstractBeanCreationStrategy {

    @Override
    public Object createBean(BeanDefinition definition, BeanFactory factory) {
        ConstructorBeanDefinition beanDefinition = (ConstructorBeanDefinition) definition;
        List<BeanDependency>      dependencies   = beanDefinition.getBeanDependencies();
        Object[]                  arguments      = new Object[0];

        if (!dependencies.isEmpty()) {
            try {
                arguments = resolveDependencies(dependencies, factory);
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