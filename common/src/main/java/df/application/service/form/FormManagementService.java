package df.application.service.form;

import df.application.exception.ApplicationException;
import org.jmouse.pipeline.PipelineManager;
import org.jmouse.pipeline.context.DefaultPipelineContext;
import df.application.provider.SpringBeanLookup;
import org.jmouse.core.context.ContextKey;
import org.jmouse.core.context.execution.Execution;
import org.springframework.context.ApplicationContext;
import org.springframework.util.MultiValueMap;

public class FormManagementService implements FormManagement {

    public static final String             PROCESS_FORM_HTML_PIPELINE    = "process-form-html";
    public static final String             DYNAMIC_FORM_HANDLER_PIPELINE = "dynamic-form-handler";
    public static final String             PRIMARY_ID                    = "PRIMARY_ID";
    public static final String             ENV_NAME                      = "ENV_NAME";
    public static final String             DEMO                          = "DEMO";
    public static final String             LOCAL                         = "LOCAL";
    public static final ContextKey<String> ACTIVE_ENVIRONMENT            = ContextKey.of(ENV_NAME, String.class);
    public static final ContextKey<String> DEFAULT_ENVIRONMENT           = ContextKey.of(ENV_NAME, String.class);

    private final PipelineManager    pipelineManager;
    private final ApplicationContext applicationContext;

    public FormManagementService(PipelineManager pipelineManager, ApplicationContext applicationContext) {
        this.pipelineManager = pipelineManager;
        this.applicationContext = applicationContext;
    }

    @Override
    public void performDynamicForm(DefaultPipelineContext context, String primaryId, MultiValueMap<String, String> postData) {
        context.setArgument(MultiValueMap.class, postData);
        context.setArgument(PRIMARY_ID, primaryId);
        context.setValue(ApplicationContext.class, applicationContext);
        context.setBeanLookup(new SpringBeanLookup());

        try {
            pipelineManager.run(DYNAMIC_FORM_HANDLER_PIPELINE, context);
        } catch (Exception e) {
            throw new ApplicationException(e, e.getMessage());
        }

    }

    @Override
    public void renderDynamicForm(DefaultPipelineContext context, String primaryId) {
        context.setArgument(PRIMARY_ID, primaryId);
        context.setArgument(ENV_NAME, DEMO);
        context.setBeanLookup(new SpringBeanLookup());

        Execution.in(ACTIVE_ENVIRONMENT, DEMO, () -> {
            try {
                pipelineManager.run(PROCESS_FORM_HTML_PIPELINE, context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
