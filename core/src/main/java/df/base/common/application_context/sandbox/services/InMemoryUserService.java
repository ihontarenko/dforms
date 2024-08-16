package df.base.common.application_context.sandbox.services;

import df.base.common.application_context.bean.BeanInjection;

public class InMemoryUserService implements UserService {

    @Override
    public String toString() {
        return "InMemoryUserService{" +
                "name='" + name + '\'' +
                '}';
    }

    @BeanInjection
    private String name;

}
