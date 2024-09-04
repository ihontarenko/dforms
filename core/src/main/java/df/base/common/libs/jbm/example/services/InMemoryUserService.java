package df.base.common.libs.jbm.example.services;

import df.base.common.libs.jbm.bean.BeanInjection;

public class InMemoryUserService implements UserService {

    @Override
    public String toString() {
        return "InMemoryUserService{name='%s'}".formatted(name);
    }

    @BeanInjection("SUPER_HERO")
    private String name;

}
