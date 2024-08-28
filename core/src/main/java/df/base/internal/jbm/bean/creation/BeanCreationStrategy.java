package df.base.internal.jbm.bean.creation;

import df.base.internal.jbm.bean.BeanFactory;
import df.base.internal.jbm.bean.definition.BeanDefinition;

public interface BeanCreationStrategy {

    Object createBean(BeanDefinition definition, BeanFactory factory);

    boolean canCreate(BeanDefinition definition);

}
