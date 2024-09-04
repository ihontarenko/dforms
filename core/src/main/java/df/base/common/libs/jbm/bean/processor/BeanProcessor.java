package df.base.common.libs.jbm.bean.processor;

import df.base.common.libs.jbm.bean.context.JbmContext;

public interface BeanProcessor {

    void process(Object bean, JbmContext context);

}
