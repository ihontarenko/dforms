package df.base.common.container.example.services;

import df.base.common.container.bean.annotation.BeanConstructor;

public class ServiceE implements ServiceInterface {

    private String name;

    @BeanConstructor
    public ServiceE(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ServiceE{name='%s'}".formatted(name);
    }
}
