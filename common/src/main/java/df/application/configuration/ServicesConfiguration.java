package df.application.configuration;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import df.application.PackageCoreRoot;
import org.jmouse.common.ast.parser.ParserContext;
import df.common.breadcrumb.BreadcrumbService;
import org.jmouse.common.dom.NodeContext;
import org.jmouse.common.dom.RendererFactory;
import df.common.i18n.Translator;
import org.jmouse.pipeline.PipelineProcessorFactory;
import org.jmouse.pipeline.definition.DefaultDefinitionProcessing;
import org.jmouse.pipeline.definition.loading.ClasspathSource;
import org.jmouse.pipeline.definition.loading.DefinitionLoader;
import org.jmouse.pipeline.definition.loading.dto.XmlDTO2DefinitionMapper;
import org.jmouse.pipeline.definition.loading.readers.XmlDefinitionReader;
import org.jmouse.pipeline.definition.model.PipelineDefinition;
import org.jmouse.pipeline.runtime.DefaultPipelineCompiler;
import org.jmouse.pipeline.runtime.PipelineCompiler;
import org.jmouse.core.events.EventManager;
import org.jmouse.core.events.EventManagerFactory;
import org.jmouse.core.proxy.DefaultProxyFactory;
import org.jmouse.core.proxy.InterceptorRegistrar;
import org.jmouse.core.proxy.ProxyFactory;
import org.jmouse.expression.parser.ParserConfigurator;
import org.jmouse.pipeline.PipelineManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

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
    public ParserContext parameterParserContext() {
        new ParserConfigurator().configure(ParserContext.CONTEXT);

        return ParserContext.CONTEXT;
    }

    @Bean
    public PipelineManager pipelineManager(PipelineDefinition definition, PipelineCompiler pipelineCompiler) {
        return new PipelineManager(definition, pipelineCompiler);
    }

    @Bean
    public DefinitionLoader definitionLoader() {
        return new DefinitionLoader(
                List.of(
                        new XmlDefinitionReader(new XmlMapper(), new XmlDTO2DefinitionMapper())
                ),
                DefaultDefinitionProcessing.defaults()
        );
    }

    @Bean
    public PipelineCompiler pipelineCompiler() {
        ProxyFactory proxyFactory = new DefaultProxyFactory();

        new InterceptorRegistrar(proxyFactory.getRegistry())
                .register(PackageCoreRoot.class);

        return new DefaultPipelineCompiler(new PipelineProcessorFactory(), proxyFactory);
    }

    @Bean
    public PipelineDefinition pipelineDefinition(DefinitionLoader loader) {
        return loader.load(new ClasspathSource("/pipeline/pipeline-DEFAULT.xml"));
    }

    @Bean
    public EventManager eventManager() {
        return EventManagerFactory.create(PackageCoreRoot.class);
    }

}
