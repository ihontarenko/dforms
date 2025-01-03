package df.common.container.bean.definition;

import df.common.container.bean.BeanCreationType;
import df.common.container.bean.BeanDependency;
import df.common.container.bean.annotation.Lifecycle;
import df.common.container.bean.creation.BeanCreationStrategy;

import java.util.List;

public interface BeanDefinition {

    default boolean isSingleton() {
        return getBeanScope() == Lifecycle.Scope.SINGLETON || getBeanScope() == Lifecycle.Scope.NON_BEAN;
    }

    default boolean isPrototype() {
        return !isSingleton();
    }

    String getBeanName();

    void setBeanName(String name);

    Class<?> getBeanClass();

    void setBeanClass(Class<?> type);

    BeanDefinition getParentDefinition();

    void setParentDefinition(BeanDefinition parent);

    List<BeanDefinition> getChildrenDefinitions();

    void addChildDefinition(BeanDefinition child);

    Lifecycle.Scope getBeanScope();

    void setBeanScope(Lifecycle.Scope scope);

    List<BeanDependency> getBeanDependencies();

    BeanCreationType getBeanCreationType();

    <T> T getBeanInstance();

    void setBeanInstance(Object instance);

    BeanCreationStrategy getBeanCreationStrategy();

    void setBeanCreationStrategy(BeanCreationStrategy strategy);

}
