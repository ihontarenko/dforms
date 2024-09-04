package df.base.common.libs.jbm.bean.creation;

import df.base.common.libs.jbm.bean.BeanFactory;
import df.base.common.libs.jbm.bean.definition.BeanDefinition;

public interface BeanCreationStrategy {

    Object createBean(BeanDefinition definition, BeanFactory factory);

    boolean canCreate(BeanDefinition definition);

}
