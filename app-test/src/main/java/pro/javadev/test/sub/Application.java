package pro.javadev.test.sub;

import pro.javadev.test.sub.application1.ConfigA;
import pro.javadev.test.sub.application2.ConfigB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication(exclude = DispatcherServletAutoConfiguration.class)
public class Application {

    public static void main(String... arguments) throws Exception {
        SpringApplication.run(Application.class, arguments);
    }

    @Bean
    public ServletRegistrationBean<DispatcherServlet> applicationA() {
        DispatcherServlet                     servlet = new DispatcherServlet();
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        context.register(ConfigA.class);
        servlet.setApplicationContext(context);

        var registrationBean = new ServletRegistrationBean<>(servlet, "/appA/*");

        registrationBean.setName("appA");

        return registrationBean;
    }

    @Bean
    public ServletRegistrationBean<DispatcherServlet> applicationB() {
        DispatcherServlet                     servlet = new DispatcherServlet();
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        context.register(ConfigB.class);
        servlet.setApplicationContext(context);

        var registrationBean = new ServletRegistrationBean<>(servlet, "/appB/*");

        registrationBean.setName("appB");

        return registrationBean;
    }
}
