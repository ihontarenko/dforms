package df.base.common.jbm.example.services;

import df.base.common.jbm.bean.BeanConstructor;
import df.base.common.jbm.bean.BeanInjection;
import df.base.common.jbm.bean.Name;

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
