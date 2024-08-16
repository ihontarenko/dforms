package df.base.common.application_context.bean.processor;

import df.base.common.application_context.bean.context.ApplicationContext;
import df.base.common.application_context.bean.context.ApplicationContextAware;

public class ApplicationContextAwareBeanProcessor implements BeanProcessor {

    @Override
    public void process(Object object, ApplicationContext context) {
        if (object instanceof ApplicationContextAware bean) {
            bean.setApplicationContext(context);
        }
    }

}
