package df.base.internal.jbm.bean.creation;

import df.base.internal.jbm.bean.BeanDependency;
import df.base.internal.jbm.bean.BeanFactory;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractBeanCreationStrategy implements BeanCreationStrategy {

    protected Object[] getArguments(List<BeanDependency> dependencies, BeanFactory factory) {
        List<Object>         arguments    = new ArrayList<>();

        for (BeanDependency dependency : dependencies) {
            if (dependency.getBeanName() != null) {
                arguments.add(factory.getBean(dependency.getBeanName()));
            } else {
                arguments.add(factory.getBean(dependency.getBeanClass()));
            }
        }

        return arguments.toArray(Object[]::new);
    }

}
