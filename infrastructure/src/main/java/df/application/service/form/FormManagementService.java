package df.application.service.form;

import df.application.exception.ApplicationException;
import org.jmouse.common.pipeline.PipelineManager;
import org.jmouse.common.pipeline.context.DefaultPipelineContext;
import df.application.provider.bean.SpringBeanLookup;
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
    public void performDynamicForm(DefaultPipelineContext ctx, String primaryId, MultiValueMap<String, String> postData) {
        ctx.setArgument(MultiValueMap.class, postData);
        ctx.setArgument(PRIMARY_ID, primaryId);
        ctx.setValue(ApplicationContext.class, applicationContext);
        ctx.setBeanLookup(new SpringBeanLookup());

        try {
            pipelineManager.run(DYNAMIC_FORM_HANDLER_PIPELINE, ctx);
        } catch (Exception e) {
            throw new ApplicationException(e, e.getMessage());
        }

    }

    @Override
    public void renderDynamicForm(DefaultPipelineContext ctx, String primaryId) {
        ctx.setArgument(PRIMARY_ID, primaryId);
        ctx.setArgument(ENV_NAME, DEMO);
        ctx.setBeanLookup(new SpringBeanLookup());

        Execution.in(ACTIVE_ENVIRONMENT, DEMO, () -> {
            try {
                pipelineManager.run(PROCESS_FORM_HTML_PIPELINE, ctx);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
