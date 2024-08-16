package df.base.common.application_context.bean.processor;

import df.base.common.application_context.bean.context.ApplicationContext;

public interface BeanProcessor {

    void process(Object bean, ApplicationContext context);

}
