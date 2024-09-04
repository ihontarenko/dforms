package df.base.configuration;

import df.base.Constants;
import df.base.common.extensions.hibernate.naming.EnhancementPhysicalNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EntityScan(Constants.ENTITY_PACKAGE_TO_SCAN)
public class HibernateConfiguration {

    @Bean
    public PhysicalNamingStrategy physicalNamingStrategy() {
        return new EnhancementPhysicalNamingStrategy();
    }

    @Bean
    public ImplicitNamingStrategy implicitNamingStrategy() {
        return new ImplicitNamingStrategyLegacyJpaImpl();
    }

}
