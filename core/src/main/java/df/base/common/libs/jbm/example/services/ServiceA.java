package df.base.common.libs.jbm.example.services;

import df.base.common.libs.jbm.bean.BeanConstructor;
import df.base.common.libs.jbm.bean.Name;

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
