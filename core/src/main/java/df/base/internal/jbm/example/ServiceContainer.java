package df.base.internal.jbm.example;

import df.base.internal.jbm.bean.Bean;
import df.base.internal.jbm.bean.BeanConstructor;
import df.base.internal.jbm.example.services.ServiceInterface;

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
