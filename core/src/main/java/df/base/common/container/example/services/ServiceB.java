package df.base.common.container.example.services;

import df.base.common.container.bean.Scope;
import df.base.common.container.bean.context.JbmContext;
import df.base.common.container.bean.context.JbmContextAware;
import df.base.common.container.bean.Bean;

@Bean(scope = Scope.PROTOTYPE)
public class ServiceB implements JbmContextAware, ServiceInterface {

    private JbmContext context;

    @Override
    public void setApplicationContext(JbmContext context) {
        this.context = context;
    }

    @Override
    public JbmContext getApplicationContext() {
        return context;
    }
}
