package df.base.common.application_context.sandbox.services;

import df.base.common.application_context.bean.BeanConstructor;
import df.base.common.application_context.bean.BeanInjection;

import java.util.StringJoiner;

public class ServiceD implements ServiceInterface {

    private String name;

    @BeanInjection("REMOTE_USER_SERVICE")
    private UserService userService;

//    @BeanInjection
    private Storage storage;

    @BeanConstructor
    public ServiceD(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ServiceD.class.getSimpleName() + "[", "]")
                .add("\n")
                .add("name='" + name + "'")
                .add("\n")
                .add("service='" + userService + "'")
                .toString();
    }
}
