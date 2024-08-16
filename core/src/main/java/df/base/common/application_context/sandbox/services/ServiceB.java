package df.base.common.application_context.sandbox.services;

import df.base.common.application_context.bean.Bean;
import df.base.common.application_context.bean.Scope;
import df.base.common.application_context.bean.context.ApplicationContext;
import df.base.common.application_context.bean.context.ApplicationContextAware;

@Bean(scope = Scope.PROTOTYPE)
public class ServiceB implements ApplicationContextAware, ServiceInterface {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return context;
    }
}
