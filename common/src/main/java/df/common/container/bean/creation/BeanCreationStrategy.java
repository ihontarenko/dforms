package df.common.container.bean.creation;

import df.common.container.bean.BeanFactory;
import df.common.container.bean.definition.BeanDefinition;

public interface BeanCreationStrategy {

    Object createBean(BeanDefinition definition, BeanFactory factory);

    boolean canCreate(BeanDefinition definition);

}
