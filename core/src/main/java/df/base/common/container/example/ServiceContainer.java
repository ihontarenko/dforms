package df.base.common.container.example;

import df.base.common.container.bean.annotation.Provide;
import df.base.common.container.bean.annotation.BeanConstructor;
import df.base.common.container.example.services.ServiceInterface;

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
