package df.base.configuration;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigrationConfiguration {

    @Bean
    public FlywayMigrationStrategy migrateStrategy() {
        return flyway -> {
            flyway.repair();
            flyway.migrate();
        };
    }

}
