package df.base.common.container.bean.creation;

import df.base.common.container.bean.BeanFactory;
import df.base.common.container.bean.definition.BeanDefinition;

public interface BeanCreationStrategy {

    Object createBean(BeanDefinition definition, BeanFactory factory);

    boolean canCreate(BeanDefinition definition);

}
