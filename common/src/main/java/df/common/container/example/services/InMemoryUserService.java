package df.common.container.example.services;

import df.common.container.bean.annotation.BeanInjection;

public class InMemoryUserService implements UserService {

    @Override
    public String toString() {
        return "InMemoryUserService{name='%s'}".formatted(name);
    }

    @BeanInjection("SUPER_HERO")
    private String name;

}
