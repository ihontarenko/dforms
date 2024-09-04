package df.base.common.libs.jbm.example.services;

import df.base.common.libs.jbm.bean.BeanInjection;

public class RemoteUserService implements UserService {

    @BeanInjection("IN_MEMORY_USER_SERVICE")
    private UserService service;

    @Override
    public String toString() {
        return "RemoteUserService{service=%s}".formatted(service);
    }

}
