package df.application.configuration;

import df.application.PackageCoreRoot;
import df.common.ast.parser.ParserContext;
import df.common.ast.token.Tokenizer;
import df.common.breadcrumb.BreadcrumbService;
import df.common.elements.NodeContext;
import df.common.elements.RendererFactory;
import df.common.i18n.Translator;
import df.common.observer.EventManager;
import df.common.observer.EventManagerFactory;
import df.common.parser.DefaultTokenizer;
import df.common.parser.TokenizerConfigurator;
import df.common.parser.parser.ParserConfigurator;
import df.common.pipeline.PipelineManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class ServicesConfiguration {

    public static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);

    @Bean
    public Translator translator(MessageSource messageSource) {
        Translator translator = new Translator(messageSource);

        LOGGER.info("CREATE {} BEAN", Translator.class.getName());

        return translator;
    }

    @Bean
    public BreadcrumbService breadcrumbService(
            @Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping mapping) {
        return new BreadcrumbService(mapping);
    }

    @Bean
    public NodeContext nodeContext() {
        return new NodeContext(new RendererFactory());
    }

    @Bean
    public Tokenizer parameterTokenizer() {
        Tokenizer     tokenizer = new DefaultTokenizer();

        new TokenizerConfigurator().configure(tokenizer);

        return tokenizer;
    }

    @Bean
    public ParserContext parameterParserContext() {
        new ParserConfigurator().configure(ParserContext.CONTEXT);

        return ParserContext.CONTEXT;
    }

    @Bean
    public PipelineManager pipelineManager() {
        return new PipelineManager("/pipeline/pipeline-DEFAULT.xml");
    }

    @Bean
    public EventManager eventManager() {
        return EventManagerFactory.create(PackageCoreRoot.class);
    }

}
