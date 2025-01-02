package df.base.common.container.example;

import df.base.common.container.bean.annotation.BeansProvider;
import df.base.common.container.bean.annotation.Name;
import df.base.common.container.example.services.ServiceA;
import df.base.common.container.example.services.ServiceB;
import df.base.common.container.example.services.ServiceInterface;
import df.base.common.container.bean.annotation.Provide;

@BeansProvider
public class ApplicationBeansProviderExample {

    @Provide("superHero")
    public String superHero() {
        return "Chuck Norris";
    }

    @Provide("wonderWoman")
    public String wonderWoman() {
        return "Gal Gadot";
    }

    @Provide("ServiceA1")
    public ServiceInterface serviceA(@Name("SUPER_HERO") String name) {
        return new ServiceA(name);
    }

    @Provide("ServiceB1")
    public ServiceInterface serviceB() {
        return new ServiceB();
    }

}
