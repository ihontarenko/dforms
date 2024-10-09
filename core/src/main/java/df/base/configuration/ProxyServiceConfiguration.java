package df.base.configuration;

import df.base.PackageCoreRoot;
import df.base.common.pipeline.PipelineManager;
import df.base.common.proxy.AnnotationProxyFactory;
import df.base.service.form.FormManagement;
import df.base.service.form.FormManagementService;
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
