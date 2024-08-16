package df.base.common.application_context.sandbox.services;

import df.base.common.application_context.bean.BeanInjection;

public class RemoteUserService implements UserService {

    @BeanInjection("IN_MEMORY_USER_SERVICE")
    private UserService service;

    @Override
    public String toString() {
        return "RemoteUserService{" +
                "service=" + service +
                '}';
    }

}
