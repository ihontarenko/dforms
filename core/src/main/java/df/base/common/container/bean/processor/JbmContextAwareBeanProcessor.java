package df.base.common.container.bean.processor;

import df.base.common.container.bean.context.JbmContext;
import df.base.common.container.bean.context.JbmContextAware;

public class JbmContextAwareBeanProcessor implements BeanProcessor {

    @Override
    public void process(Object object, JbmContext context) {
        if (object instanceof JbmContextAware bean) {
            bean.setApplicationContext(context);
        }
    }

}
