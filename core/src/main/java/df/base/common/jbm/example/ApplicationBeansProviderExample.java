package df.base.common.jbm.example;

import df.base.common.jbm.bean.Bean;
import df.base.common.jbm.bean.BeansProvider;
import df.base.common.jbm.bean.Name;
import df.base.common.jbm.example.services.ServiceA;
import df.base.common.jbm.example.services.ServiceB;
import df.base.common.jbm.example.services.ServiceInterface;

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
