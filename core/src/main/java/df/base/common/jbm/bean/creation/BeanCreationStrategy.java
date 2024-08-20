package df.base.common.jbm.bean.creation;

import df.base.common.jbm.bean.BeanFactory;
import df.base.common.jbm.bean.definition.BeanDefinition;

public interface BeanCreationStrategy {

    Object createBean(BeanDefinition definition, BeanFactory factory);

    boolean canCreate(BeanDefinition definition);

}
