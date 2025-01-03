package df.common.container.example.services;

import df.common.container.bean.annotation.BeanInjection;

public class RemoteUserService implements UserService {

    @BeanInjection("IN_MEMORY_USER_SERVICE")
    private UserService service;

    @Override
    public String toString() {
        return "RemoteUserService{service=%s}".formatted(service);
    }

}