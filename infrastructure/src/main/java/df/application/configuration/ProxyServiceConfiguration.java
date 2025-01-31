package df.application.configuration;

import df.application.PackageCoreRoot;
import df.common.pipeline.PipelineManager;
import org.jmouse.core.proxy.AnnotationProxyFactory;
import df.application.service.form.FormManagement;
import df.application.service.form.FormManagementService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.jmouse.core.proxy.ProxyFactory;

@Configuration
public class ProxyServiceConfiguration {

    @Bean
    public FormManagement getFormManagement(PipelineManager pipelineManager, ApplicationContext applicationContext) {
        ProxyFactory proxyFactory = new AnnotationProxyFactory(PackageCoreRoot.class);

        return proxyFactory.createProxy(new FormManagementService(pipelineManager, applicationContext));
    }

}
