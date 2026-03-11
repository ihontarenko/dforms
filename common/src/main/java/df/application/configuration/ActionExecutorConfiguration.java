package df.application.configuration;

import df.application.PackageCoreRoot;
import org.jmouse.action.*;
import org.jmouse.action.adapter.el.ActionELModule;
import org.jmouse.action.adapter.el.ActionExpressionAdapter;
import org.jmouse.action.adapter.mapper.ActionDefinitionMapper;
import org.jmouse.action.smoke.SmokeA;
import org.jmouse.action.support.ActionAnnotationProcessor;
import org.jmouse.core.annotation.AnnotationBootstrapper;
import org.jmouse.core.annotation.AnnotationDiscovery;
import org.jmouse.core.annotation.AnnotationProcessingContext;
import org.jmouse.core.invoke.ContextMethodArgumentResolver;
import org.jmouse.core.invoke.InvocationRequestMethodArgumentResolver;
import org.jmouse.core.invoke.MethodArgumentResolverComposite;
import org.jmouse.core.invoke.MethodInvoker;
import org.jmouse.core.mapping.Mapper;
import org.jmouse.core.mapping.Mappers;
import org.jmouse.el.ExpressionLanguage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ActionExecutorConfiguration {

    @Bean
    public ActionExecutor actionExecutor() {
        ActionRegistry registry = new ConfigurableActionRegistry(
                new SimpleActionRegistry(), Mappers.defaultMapper()
        ).unwrap();

        AnnotationBootstrapper.defaults(AnnotationDiscovery.defaults()).bootstrap(
                new AnnotationProcessingContext.Default(),
                List.of(new ActionAnnotationProcessor.Default(registry, getMethodInvoker(Mappers.defaultMapper()))),
                PackageCoreRoot.class
        );

        return ActionExecutor.defaults(registry);
    }

    @Bean
    public ActionExpressionAdapter actionExpressionAdapter(
            ExpressionLanguage expressionLanguage, ActionExecutor actionExecutor
    ) {
        ActionELModule.configure(expressionLanguage);
        return new ActionExpressionAdapter(expressionLanguage, actionExecutor);
    }

    public static MethodInvoker getMethodInvoker(Mapper objectMapper) {
        ActionDefinitionMapper mapper = new ActionDefinitionMapper(objectMapper);

        MethodArgumentResolverComposite resolvers = new MethodArgumentResolverComposite()
                .addResolver(new ActionRequestMethodArgumentResolver())
                .addResolver(new ContextMethodArgumentResolver())
                .addResolver(new InvocationRequestMethodArgumentResolver())
                .addResolver(new MappedActionArgumentResolver(mapper));

        return new MethodInvoker.Default(resolvers);
    }

}
