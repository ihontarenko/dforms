package df.base.internal.jbm.example.services;

import df.base.internal.jbm.bean.BeanConstructor;

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
