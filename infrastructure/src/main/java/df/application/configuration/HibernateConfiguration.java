package df.application.configuration;

import df.application.Constants;
import df.common.extensions.hibernate.naming.EnhancementPhysicalNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(Constants.ENTITY_PACKAGE_TO_SCAN)
public class HibernateConfiguration {

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hp -> {};
    }

    @Bean
    public PhysicalNamingStrategy physicalNamingStrategy() {
        return new EnhancementPhysicalNamingStrategy();
    }

    @Bean
    public ImplicitNamingStrategy implicitNamingStrategy() {
        return new ImplicitNamingStrategyLegacyJpaImpl();
    }

}
