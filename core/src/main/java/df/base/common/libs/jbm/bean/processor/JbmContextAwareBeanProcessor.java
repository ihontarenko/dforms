package df.base.common.libs.jbm.bean.processor;

import df.base.common.libs.jbm.bean.context.JbmContext;
import df.base.common.libs.jbm.bean.context.JbmContextAware;

public class JbmContextAwareBeanProcessor implements BeanProcessor {

    @Override
    public void process(Object object, JbmContext context) {
        if (object instanceof JbmContextAware bean) {
            bean.setApplicationContext(context);
        }
    }

}
