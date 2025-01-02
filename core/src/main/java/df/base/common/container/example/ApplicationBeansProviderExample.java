package df.base.common.container.example;

import df.base.common.container.bean.BeansProvider;
import df.base.common.container.bean.Name;
import df.base.common.container.example.services.ServiceA;
import df.base.common.container.example.services.ServiceB;
import df.base.common.container.example.services.ServiceInterface;
import df.base.common.container.bean.Bean;

@BeansProvider
public class ApplicationBeansProviderExample {

    @Bean("superHero")
    public String superHero() {
        return "Chuck Norris";
    }

    @Bean("wonderWoman")
    public String wonderWoman() {
        return "Gal Gadot";
    }

    @Bean("ServiceA1")
    public ServiceInterface serviceA(@Name("SUPER_HERO") String name) {
        return new ServiceA(name);
    }

    @Bean("ServiceB1")
    public ServiceInterface serviceB() {
        return new ServiceB();
    }

}
