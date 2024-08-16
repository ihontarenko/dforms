package df.base.common.application_context.bean.creation;

import df.base.common.application_context.bean.BeanFactory;
import df.base.common.application_context.bean.definition.BeanDefinition;

public interface BeanCreationStrategy {

    Object createBean(BeanDefinition definition, BeanFactory factory);

    boolean canCreate(BeanDefinition definition);

}
