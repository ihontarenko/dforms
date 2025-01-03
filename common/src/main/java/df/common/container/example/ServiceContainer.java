package df.common.container.example;

import df.common.container.bean.annotation.Provide;
import df.common.container.bean.annotation.BeanConstructor;
import df.common.container.example.services.ServiceInterface;

@Provide
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
