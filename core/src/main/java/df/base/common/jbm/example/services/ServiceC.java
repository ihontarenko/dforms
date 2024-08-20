package df.base.common.jbm.example.services;

import df.base.common.jbm.bean.BeanConstructor;

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
