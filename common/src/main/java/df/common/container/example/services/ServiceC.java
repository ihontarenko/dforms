package df.common.container.example.services;

import df.common.container.bean.annotation.BeanConstructor;

public class ServiceC implements ServiceInterface {

    private String name;

    @BeanConstructor
    public ServiceC(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ServiceC{name='%s'}".formatted(name);
    }
}
