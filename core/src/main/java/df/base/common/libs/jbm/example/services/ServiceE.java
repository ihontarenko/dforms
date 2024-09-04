package df.base.common.libs.jbm.example.services;

import df.base.common.libs.jbm.bean.BeanConstructor;

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
