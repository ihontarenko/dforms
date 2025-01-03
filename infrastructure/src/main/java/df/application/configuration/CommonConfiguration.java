package df.application.configuration;

import df.application.Constants;
import df.common.extensions.persistence.entity_graph.support.EntityGraphRepositoryFactoryBean;
import df.common.validation.custom.ValidationContextArgumentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.List;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(
        basePackages = Constants.ENTITY_PACKAGE_TO_SCAN,
        repositoryFactoryBeanClass = EntityGraphRepositoryFactoryBean.class)
@ComponentScan(basePackages = {Constants.BASE_PACKAGE_TO_SCAN})
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = {"df.application.property"})
public class CommonConfiguration implements WebMvcConfigurer {

    public static final Logger LOGGER = LoggerFactory.getLogger(CommonConfiguration.class);

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source   = new ReloadableResourceBundleMessageSource();
        ResourcePatternResolver               resolver = new PathMatchingResourcePatternResolver();

        source.setResourceLoader(resolver);
        source.addBasenames("classpath:locale/main", "classpath:locale/text", "classpath:locale/messages");
        source.setFallbackToSystemLocale(true);
        source.setDefaultEncoding("UTF-8");

        LOGGER.info("CREATE {} BEAN", ReloadableResourceBundleMessageSource.class.getName());

        return source;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();

        interceptor.setParamName("locale");

        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ValidationContextArgumentResolver());
    }
}
