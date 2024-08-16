package df.base.common.application_context.sandbox.services;

import df.base.common.application_context.bean.BeanConstructor;

import java.util.StringJoiner;

public class ServiceE implements ServiceInterface {

    private String name;

    @BeanConstructor
    public ServiceE(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ServiceE.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
    }
}
