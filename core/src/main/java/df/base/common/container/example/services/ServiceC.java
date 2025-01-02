package df.base.common.container.example.services;

import df.base.common.container.bean.BeanConstructor;

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
