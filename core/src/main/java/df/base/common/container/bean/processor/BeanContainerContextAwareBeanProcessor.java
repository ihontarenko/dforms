package df.base.common.container.bean.processor;

import df.base.common.container.bean.context.BeanContainerContext;
import df.base.common.container.bean.context.JbmContextAware;

public class BeanContainerContextAwareBeanProcessor implements BeanProcessor {

    @Override
    public void process(Object object, BeanContainerContext context) {
        if (object instanceof JbmContextAware bean) {
            bean.setApplicationContext(context);
        }
    }

}
