package df.base.common.application_context.bean.creation;

import df.base.common.application_context.bean.BeanDependency;
import df.base.common.application_context.bean.BeanFactory;
import df.base.common.application_context.bean.ObjectCreationException;
import df.base.common.application_context.bean.definition.BeanDefinition;
import df.base.common.application_context.bean.definition.ConstructorBeanDefinition;

import java.util.List;

import static df.base.common.application_context.ReflectionUtils.instantiate;

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
