package df.admin;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "df.admin.controller")
public class AdminWebConfig {
    // Тут будуть свої контролери, ресолвери, конфігурація
}