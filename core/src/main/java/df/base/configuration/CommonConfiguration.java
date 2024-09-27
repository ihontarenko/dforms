package df.base.configuration;

import df.base.Constants;
import df.base.common.elements.NodeContext;
import df.base.common.elements.RendererFactory;
import df.base.common.i18n.Translator;
import df.base.common.extensions.persistence.entity_graph.support.EntityGraphRepositoryFactoryBean;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.token.Tokenizer;
import df.base.common.pipeline.PipelineManager;
import df.base.common.validation.custom.ValidationContextArgumentResolver;
import df.base.common.parser.DefaultTokenizer;
import df.base.common.parser.configurator.AnnotationParserConfigurator;
import df.base.common.parser.configurator.AnnotationTokenizerConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(
        basePackages = Constants.ENTITY_PACKAGE_TO_SCAN,
        repositoryFactoryBeanClass = EntityGraphRepositoryFactoryBean.class)
@ComponentScan(basePackages = {Constants.BASE_PACKAGE_TO_SCAN})
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = {"df.base.property"})
public class CommonConfiguration implements WebMvcConfigurer {

    public static final Logger LOGGER = LoggerFactory.getLogger(CommonConfiguration.class);

    @Bean
    public Translator translator(MessageSource messageSource) {
        Translator translator = new Translator(messageSource);

        LOGGER.info("CREATE {} BEAN", Translator.class.getName());

        return translator;
    }

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

        LOGGER.info("AVAILABLE LOCALES: {}", locales);

        return locales;
    }

    @Bean
    public NodeContext nodeContext() {
        return new NodeContext(new RendererFactory());
    }

    @Bean
    public Tokenizer parameterTokenizer() {
        Tokenizer     tokenizer = new DefaultTokenizer();

        new AnnotationTokenizerConfigurator().configure(tokenizer);

        return tokenizer;
    }

    @Bean
    public ParserContext parameterParserContext() {
        new AnnotationParserConfigurator().configure(ParserContext.CONTEXT);

        return ParserContext.CONTEXT;
    }

    @Bean
    public PipelineManager pipelineManager() {
        return new PipelineManager("/pipeline/pipeline-DEFAULT.xml");
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
