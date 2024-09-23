package df.base.service.form;

import df.base.common.context.ArgumentsContext;
import df.base.common.context.ResultContext;
import df.base.common.context.bean_provider.SpringBeanProvider;
import df.base.common.exception.ApplicationException;
import df.base.common.pipeline.PipelineManager;
import df.base.common.pipeline.context.DefaultPipelineContext;
import df.base.common.pipeline.context.PipelineContext;
import df.base.persistence.entity.form.Form;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class FormManagmentService {

    public static final String DYNAMIC_FORM_HANDLER_PIPELINE = "dynamic-form-handler";
    public static final String PRIMARY_ID                    = "PRIMARY_ID";
    public static final String ENV_NAME                      = "ENV_NAME";
    public static final String DEMO                          = "DEMO";

    private final PipelineManager    pipelineManager;
    private final ApplicationContext applicationContext;

    public FormManagmentService(PipelineManager pipelineManager, ApplicationContext applicationContext) {
        this.pipelineManager = pipelineManager;
        this.applicationContext = applicationContext;
    }

    public PipelineContext performDynamicForm(String primaryId, MultiValueMap<String, String> postData) {
        DefaultPipelineContext ctx = (DefaultPipelineContext) pipelineManager.getContext();

        ctx.setArgument(MultiValueMap.class, postData);
        ctx.setProperty(ApplicationContext.class, applicationContext);
        ctx.setArgument(PRIMARY_ID, primaryId);

        try {
            pipelineManager.runPipeline(DYNAMIC_FORM_HANDLER_PIPELINE);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return ctx;
    }

    public PipelineContext renderDynamicForm(String primaryId, Form entity) {
        PipelineContext  ctx       = pipelineManager.getContext();
        ArgumentsContext arguments = ctx.getArgumentsContext();

        ctx.setBeanProvider(new SpringBeanProvider());

        arguments.setArgument(PRIMARY_ID, primaryId);
        arguments.setArgument(ENV_NAME, DEMO);
        arguments.setArgument(Form.class, entity);

        try {
            pipelineManager.runPipeline("process-form-html");
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return ctx;
    }

}
