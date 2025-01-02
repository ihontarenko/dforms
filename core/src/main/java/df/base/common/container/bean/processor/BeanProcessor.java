package df.base.common.container.bean.processor;

import df.base.common.container.bean.context.BeanContainerContext;

public interface BeanProcessor {

    void process(Object bean, BeanContainerContext context);

}
