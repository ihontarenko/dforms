package df.base.common.jbm.bean.processor;

import df.base.common.jbm.bean.context.JbmContext;
import df.base.common.jbm.bean.context.ApplicationContextAware;

public class ApplicationContextAwareBeanProcessor implements BeanProcessor {

    @Override
    public void process(Object object, JbmContext context) {
        if (object instanceof ApplicationContextAware bean) {
            bean.setApplicationContext(context);
        }
    }

}
