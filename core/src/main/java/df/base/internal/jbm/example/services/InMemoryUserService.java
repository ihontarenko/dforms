package df.base.internal.jbm.example.services;

import df.base.internal.jbm.bean.BeanInjection;

public class InMemoryUserService implements UserService {

    @Override
    public String toString() {
        return "InMemoryUserService{name='%s'}".formatted(name);
    }

    @BeanInjection("SUPER_HERO")
    private String name;

}
