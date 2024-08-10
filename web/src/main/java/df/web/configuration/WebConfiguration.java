package df.web.configuration;

import df.base.common.resource.ContentHashVersionStrategy;
import df.base.configuration.ParentConfiguration;
import df.base.property.ApplicationProperties;
import df.web.common.pebble.PebbleExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import static java.util.Objects.requireNonNull;

@Configuration
@EnableAutoConfiguration
@ComponentScan("df.web")
@ConfigurationPropertiesScan(basePackages = {"df.web.property"})
@Import(ParentConfiguration.class)
public class WebConfiguration implements WebMvcConfigurer {

    private final ApplicationContext    context;
    private final ApplicationProperties properties;
    private final Environment           environment;

    public WebConfiguration(ApplicationContext context, ApplicationProperties properties, Environment environment) {
        this.environment = environment;
        this.context = context;
        this.properties = properties;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();

        resolver.setCookieName(properties.getLocale().getCookieName());
        resolver.setCookieMaxAge(properties.getLocale().getCookieMaxAge());
        resolver.setCookiePath(requireNonNull(
                environment.getProperty("server.servlet.context-path")
        ));

        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/assets/")
                .setCachePeriod(2629743)
                .resourceChain(true)
                .addResolver(new EncodedResourceResolver())
                .addResolver(new VersionResourceResolver()
                        .addVersionStrategy(new ContentHashVersionStrategy(), "/**")
                );
    }

    @Bean
    public PebbleExtension pebbleExtension(ApplicationContext context) {
        return new PebbleExtension(context);
    }

}