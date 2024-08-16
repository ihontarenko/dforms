package df.base.common.application_context.sandbox.services;

import df.base.common.application_context.bean.BeanConstructor;

import java.util.StringJoiner;

public class ServiceC implements ServiceInterface {

    private String name;

    @BeanConstructor
    public ServiceC(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ServiceC.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
    }
}
