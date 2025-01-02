package df.base.common.container.bean.processor;

import df.base.common.container.bean.context.JbmContext;

public interface BeanProcessor {

    void process(Object bean, JbmContext context);

}
