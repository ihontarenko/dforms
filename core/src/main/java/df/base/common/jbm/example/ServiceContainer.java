package df.base.common.jbm.example;

import df.base.common.jbm.bean.Bean;
import df.base.common.jbm.bean.BeanConstructor;
import df.base.common.jbm.example.services.ServiceInterface;

@Bean
public class ServiceContainer {

    private ServiceInterface serviceA;

    @BeanConstructor
    public ServiceContainer(ServiceInterface serviceA) {
        this.serviceA = serviceA;
    }

    @Override
    public String toString() {
        return "ServiceContainer{serviceA=%s}".formatted(serviceA);
    }
}
