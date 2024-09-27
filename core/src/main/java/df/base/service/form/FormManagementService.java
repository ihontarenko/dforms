package df.base.service.form;

import df.base.common.context.provider.bean.SpringBeanProvider;
import df.base.common.exception.ApplicationException;
import df.base.common.pipeline.PipelineManager;
import df.base.common.pipeline.context.DefaultPipelineContext;
import org.springframework.context.ApplicationContext;
import org.springframework.util.MultiValueMap;

public class FormManagementService implements FormManagement {

    public static final String PROCESS_FORM_HTML_PIPELINE    = "process-form-html";
    public static final String DYNAMIC_FORM_HANDLER_PIPELINE = "dynamic-form-handler";
    public static final String PRIMARY_ID                    = "PRIMARY_ID";
    public static final String ENV_NAME                      = "ENV_NAME";
    public static final String DEMO                          = "DEMO";

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
        ctx.setProperty(ApplicationContext.class, applicationContext);
        ctx.setBeanProvider(new SpringBeanProvider());

        try {
            pipelineManager.runPipeline(DYNAMIC_FORM_HANDLER_PIPELINE, ctx);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    @Override
    public void renderDynamicForm(DefaultPipelineContext ctx, String primaryId) {
        ctx.setArgument(PRIMARY_ID, primaryId);
        ctx.setArgument(ENV_NAME, DEMO);
        ctx.setBeanProvider(new SpringBeanProvider());

        try {
            pipelineManager.runPipeline(PROCESS_FORM_HTML_PIPELINE, ctx);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

}
