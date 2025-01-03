package df.common.container.bean.processor;

import df.common.container.bean.context.BeanContainerContext;

public interface BeanProcessor {

    void process(Object bean, BeanContainerContext context);

}
