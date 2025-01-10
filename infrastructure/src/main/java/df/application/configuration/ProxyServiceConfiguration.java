package df.application.configuration;

import df.application.PackageCoreRoot;
import df.common.pipeline.PipelineManager;
import svit.proxy.AnnotationProxyFactory;
import df.application.service.form.FormManagement;
import df.application.service.form.FormManagementService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyServiceConfiguration {

    @Bean
    public FormManagement getFormManagment(PipelineManager pipelineManager, ApplicationContext applicationContext) {
        return new AnnotationProxyFactory(new FormManagementService(
                pipelineManager, applicationContext), PackageCoreRoot.class).getProxy();
    }

}
