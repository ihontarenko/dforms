package df.common.container.example.services;

import df.common.container.bean.annotation.Lifecycle;
import df.common.container.bean.context.BeanContainerContext;
import df.common.container.bean.context.JbmContextAware;
import df.common.container.bean.annotation.Provide;

@Provide(scope = Lifecycle.Scope.PROTOTYPE)
public class ServiceB implements JbmContextAware, ServiceInterface {

    private BeanContainerContext context;

    @Override
    public void setApplicationContext(BeanContainerContext context) {
        this.context = context;
    }

    @Override
    public BeanContainerContext getApplicationContext() {
        return context;
    }
}
