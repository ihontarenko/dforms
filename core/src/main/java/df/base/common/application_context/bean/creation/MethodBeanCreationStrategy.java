package df.base.common.application_context.bean.creation;

import df.base.common.application_context.ReflectionUtils;
import df.base.common.application_context.bean.BeanDependency;
import df.base.common.application_context.bean.BeanFactory;
import df.base.common.application_context.bean.ObjectCreationException;
import df.base.common.application_context.bean.definition.BeanDefinition;
import df.base.common.application_context.bean.definition.ConstructorBeanDefinition;
import df.base.common.application_context.bean.definition.DuplicateBeanDefinitionException;
import df.base.common.application_context.bean.definition.MethodBeanDefinition;

import java.lang.reflect.Method;
import java.util.List;

public class MethodBeanCreationStrategy extends AbstractBeanCreationStrategy {

    @Override
    public Object createBean(BeanDefinition definition, BeanFactory factory) {
        MethodBeanDefinition      beanDefinition    = (MethodBeanDefinition) definition;
        Method                    factoryMethod     = beanDefinition.getBeanFactoryMethod();
        Class<?>                  factoryClass      = factoryMethod.getDeclaringClass();
        ConstructorBeanDefinition factoryDefinition = (ConstructorBeanDefinition) factory.createBeanDefinition(factoryClass);

        try {
            factory.registerBeanDefinition(factoryDefinition);
        } catch (DuplicateBeanDefinitionException e) {
            // no-ops
        }

        List<BeanDependency> dependencies = beanDefinition.getBeanDependencies();
        Object               object       = factory.getBean(factoryDefinition.getBeanName());
        Object[]             arguments    = new Object[0];

        beanDefinition.setBeanFactoryObject(object);

        if (!dependencies.isEmpty()) {
            try {
                arguments = getArguments(dependencies, factory);
            } catch (RuntimeException exception) {
                throw new ObjectCreationException(
                        "UNABLE TO CREATE BEAN VIA METHOD STRATEGY FOR BEAN TYPE: " + definition.getBeanClass(), exception);
            }
        }

        return ReflectionUtils.invokeMethod(object, factoryMethod, arguments);
    }

    @Override
    public boolean canCreate(BeanDefinition definition) {
        return MethodBeanDefinition.class.isAssignableFrom(definition.getClass());
    }
}
