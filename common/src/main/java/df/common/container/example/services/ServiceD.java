package df.common.container.example.services;

import df.common.container.bean.annotation.BeanConstructor;
import df.common.container.bean.annotation.BeanInjection;
import df.common.container.bean.annotation.Name;

public class ServiceD implements ServiceInterface {

    private String name;

    @BeanInjection("REMOTE_USER_SERVICE")
    private UserService userService;

    @BeanInjection
    private Storage storage;

    @BeanConstructor
    public ServiceD(@Name("WONDER_WOMAN") String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ServiceD{name='%s', userService=%s, storage=%s}".formatted(name, userService, storage);
    }
}
