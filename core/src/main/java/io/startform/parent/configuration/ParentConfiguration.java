package io.startform.parent.configuration;

import io.startform.parent.library.BeansHolder;
import io.startform.parent.property.ApplicationProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.io.IOException;

import static io.startform.parent.utils.Strings.substringBetween;
import static java.lang.String.format;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"io.startform.parent"})
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = {"io.startform.parent.property"})
public class ParentConfiguration implements WebMvcConfigurer {

    private final ApplicationProperties properties;

    public ParentConfiguration(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        source.setResourceLoader(resolver);
        source.addBasenames("classpath:locale/main");
        source.setFallbackToSystemLocale(true);
        source.setDefaultEncoding("UTF-8");

        try {
            Resource[] resources = resolver.getResources("classpath*:locale/shared/**/*.properties");
            for (Resource resource : resources) {
                source.addBasenames(format("classpath:%s",
                        substringBetween("locale", "_",
                                resource.getURI().toString())));
            }
        } catch (IOException ignore) {
            // ignore
        }

        return source;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();

        interceptor.setParamName("locale");

        return interceptor;
    }

    @Bean
    public BeansHolder beansHolder() {
        return BeansHolder.INSTANCE;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}
