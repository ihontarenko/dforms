package df.base.common.container.example;

import df.base.common.container.bean.Bean;
import df.base.common.container.bean.BeanConstructor;
import df.base.common.container.example.services.ServiceInterface;

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
