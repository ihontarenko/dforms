package df.base.internal.jbm.bean.processor;

import df.base.internal.jbm.bean.context.JbmContext;

public interface BeanProcessor {

    void process(Object bean, JbmContext context);

}
