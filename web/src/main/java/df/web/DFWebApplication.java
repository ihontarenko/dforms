package df.web;

import df.admin.AdminWebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@EnableJpaRepositories
@SpringBootApplication
public class DFWebApplication {

    public static void main(String... arguments) {
        SpringApplication.run(DFWebApplication.class, arguments);
    }

    @Bean
    public ServletRegistrationBean<DispatcherServlet> adminDispatcherServlet() {
        // Створюємо окремий контекст
        AnnotationConfigWebApplicationContext adminContext = new AnnotationConfigWebApplicationContext();
        adminContext.register(AdminWebConfig.class);

        DispatcherServlet adminServlet = new DispatcherServlet(adminContext);

        ServletRegistrationBean<DispatcherServlet> registration = new ServletRegistrationBean<>(adminServlet, "/admin/*");
        registration.setName("adminDispatcherServlet");
        registration.setLoadOnStartup(1);

        return registration;
    }

}
