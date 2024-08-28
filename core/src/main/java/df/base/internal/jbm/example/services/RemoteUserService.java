package df.base.internal.jbm.example.services;

import df.base.internal.jbm.bean.BeanInjection;

public class RemoteUserService implements UserService {

    @BeanInjection("IN_MEMORY_USER_SERVICE")
    private UserService service;

    @Override
    public String toString() {
        return "RemoteUserService{service=%s}".formatted(service);
    }

}
