package df.base.common.jbm.bean.processor;

import df.base.common.jbm.bean.context.JbmContext;

public interface BeanProcessor {

    void process(Object bean, JbmContext context);

}
