package df.base.common.container.bean.definition;

import df.base.common.container.bean.BeanCreationType;
import df.base.common.container.bean.BeanDependency;
import df.base.common.container.bean.Scope;
import df.base.common.container.bean.creation.BeanCreationStrategy;

import java.util.List;

public interface BeanDefinition {

    default boolean isSingleton() {
        return getBeanScope() == Scope.SINGLETON || getBeanScope() == Scope.NON_BEAN;
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

    Scope getBeanScope();

    void setBeanScope(Scope scope);

    List<BeanDependency> getBeanDependencies();

    BeanCreationType getBeanCreationType();

    <T> T getBeanInstance();

    void setBeanInstance(Object instance);

    BeanCreationStrategy getBeanCreationStrategy();

    void setBeanCreationStrategy(BeanCreationStrategy strategy);

}
