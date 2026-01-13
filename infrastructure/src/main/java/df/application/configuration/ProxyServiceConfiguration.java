package df.application.configuration;

import df.application.PackageCoreRoot;
import org.jmouse.common.pipeline.PipelineManager;
import df.application.service.form.FormManagement;
import df.application.service.form.FormManagementService;
import org.jmouse.core.proxy.DefaultProxyFactory;
import org.jmouse.core.proxy.InterceptorRegistrar;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyServiceConfiguration {

    @Bean
    public FormManagement getFormManagement(PipelineManager pipelineManager, ApplicationContext applicationContext) {
        DefaultProxyFactory proxyFactory = new DefaultProxyFactory();

        new InterceptorRegistrar(proxyFactory.getRegistry()).register(PackageCoreRoot.class);

        return proxyFactory.createProxy(new FormManagementService(pipelineManager, applicationContext));
    }

}
