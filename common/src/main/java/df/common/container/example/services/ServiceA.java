package df.common.container.example.services;

import df.common.container.bean.annotation.BeanConstructor;
import df.common.container.bean.annotation.Name;

public class ServiceA implements ServiceInterface{

    private String name;

    @BeanConstructor
    public ServiceA(@Name("WONDER_WOMAN") String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ServiceA{name='%s'}".formatted(name);
    }
}
