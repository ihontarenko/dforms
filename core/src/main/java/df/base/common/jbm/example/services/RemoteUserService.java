package df.base.common.jbm.example.services;

import df.base.common.jbm.bean.BeanInjection;

public class RemoteUserService implements UserService {

    @BeanInjection("IN_MEMORY_USER_SERVICE")
    private UserService service;

    @Override
    public String toString() {
        return "RemoteUserService{service=%s}".formatted(service);
    }

}
