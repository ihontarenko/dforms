package df.base.common.application_context.sandbox;

import df.base.common.application_context.bean.Bean;
import df.base.common.application_context.bean.BeanConstructor;
import df.base.common.application_context.sandbox.services.ServiceInterface;

import java.util.StringJoiner;

@Bean
public class ServiceContainer {

    private ServiceInterface serviceA;

    @BeanConstructor
    public ServiceContainer(ServiceInterface serviceA) {
        this.serviceA = serviceA;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ServiceContainer.class.getSimpleName() + "[", "]")
                .add("serviceA=" + serviceA)
                .toString();
    }
}
