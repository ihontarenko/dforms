package df.common.container.bean.creation;

import df.common.container.bean.BeanDependency;
import df.common.container.bean.BeanFactory;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractBeanCreationStrategy implements BeanCreationStrategy {

    protected Object[] resolveDependencies(List<BeanDependency> dependencies, BeanFactory factory) {
        List<Object> arguments = new ArrayList<>();

        for (BeanDependency dependency : dependencies) {
            arguments.add(factory.getBean(dependency.getBeanClass(), dependency.getBeanName()));
        }

        return arguments.toArray(Object[]::new);
    }

}
