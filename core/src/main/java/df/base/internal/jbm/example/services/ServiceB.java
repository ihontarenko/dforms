package df.base.internal.jbm.example.services;

import df.base.internal.jbm.bean.Bean;
import df.base.internal.jbm.bean.Scope;
import df.base.internal.jbm.bean.context.JbmContext;
import df.base.internal.jbm.bean.context.JbmContextAware;

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
