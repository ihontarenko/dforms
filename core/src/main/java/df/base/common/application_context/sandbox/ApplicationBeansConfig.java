package df.base.common.application_context.sandbox;

import df.base.common.application_context.bean.Bean;
import df.base.common.application_context.bean.BeanConfiguration;
import df.base.common.application_context.sandbox.services.ServiceA;
import df.base.common.application_context.sandbox.services.ServiceB;
import df.base.common.application_context.sandbox.services.ServiceInterface;

@BeanConfiguration
public class ApplicationBeansConfig {

    @Bean
    public String userName() {
        return "Chuck Norris";
    }

    @Bean("ServiceA1")
    public ServiceInterface serviceA(String name) {
        return new ServiceA(name);
    }

    @Bean("ServiceB1")
    public ServiceInterface serviceB() {
        return new ServiceB();
    }

}
