package df.base.common.jbm.example.services;

import df.base.common.jbm.bean.Bean;
import df.base.common.jbm.bean.Scope;
import df.base.common.jbm.bean.context.JbmContext;
import df.base.common.jbm.bean.context.JbmContextAware;

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