package df.parent.configuration;

import df.parent.library.BeansHolder;
import df.parent.library.i18n.Translator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static df.parent.utils.Strings.substringBetween;
import static java.lang.String.format;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "df.parent.jpa")
@ComponentScan(basePackages = {"df.parent"})
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = {"df.parent.property"})
public class ParentConfiguration implements WebMvcConfigurer {

    @Bean
    public Translator translator(MessageSource messageSource, BeansHolder holder) {
        Translator translator = new Translator(messageSource);

        holder.set(Translator.class, translator);

        return translator;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source   = new ReloadableResourceBundleMessageSource();
        ResourcePatternResolver               resolver = new PathMatchingResourcePatternResolver();

        source.setResourceLoader(resolver);
        source.addBasenames("classpath:locale/main");
        source.setFallbackToSystemLocale(true);
        source.setDefaultEncoding("ISO-8859-1");

        try {
            Resource[] resources = resolver.getResources("classpath*:locale/shared/**/*.properties");
            for (Resource resource : resources) {
                source.addBasenames(format("classpath:%s",
                        substringBetween("locale", "_",
                                resource.getURI().toString())));
            }
        } catch (IOException ignore) { }

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

    @Bean("locales")
    @Scope(ConfigurableListableBeanFactory.SCOPE_SINGLETON)
    public Set<Locale> availableLocales(@Value("classpath:locale/**.properties") Resource[] resources) {
        Set<Locale> locales = Arrays.stream(resources).map(resource -> {
            String fileName = null;
            try {
                fileName = resource.getFile().getName();
            } catch (IOException ignore) { }

            Objects.requireNonNull(fileName);

            int    end      = fileName.lastIndexOf('.');
            int    start    = fileName.lastIndexOf('_');

            if (start == -1) {
                return null;
            }

            return Locale.forLanguageTag(fileName.substring(start + 1, end));
        }).collect(Collectors.toSet());

        locales.removeIf(Objects::isNull);
        locales.add(Locale.getDefault());

        return locales;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}
