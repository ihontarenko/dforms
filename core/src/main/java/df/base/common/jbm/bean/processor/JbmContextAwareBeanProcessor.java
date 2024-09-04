package df.base.common.jbm.bean.processor;

import df.base.common.jbm.bean.context.JbmContext;
import df.base.common.jbm.bean.context.JbmContextAware;

public class JbmContextAwareBeanProcessor implements BeanProcessor {

    @Override
    public void process(Object object, JbmContext context) {
        if (object instanceof JbmContextAware bean) {
            bean.setApplicationContext(context);
        }
    }

}