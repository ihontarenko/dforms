package df.base.service.bc;

import df.base.common.context.ArgumentsContext;
import df.base.common.context.provider.bean.SpringBeanProvider;
import df.base.common.exception.ApplicationException;
import df.base.common.pipeline.PipelineManager;
import df.base.common.pipeline.context.PipelineContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ClassManagmentService {

    public static final String             BEAN_CONSOLE_PIPELINE = "bean-console";
    private final       PipelineManager    pipelineManager;
    private final       ApplicationContext applicationContext;
    private final       ClassService       classService;

    public ClassManagmentService(PipelineManager pipelineManager, ApplicationContext applicationContext, ClassService classService) {
        this.pipelineManager = pipelineManager;
        this.applicationContext = applicationContext;
        this.classService = classService;
    }

    public void performPipeline(PipelineContext ctx) {
        ctx.setProperty(ApplicationContext.class, applicationContext);
        ctx.setBeanProvider(new SpringBeanProvider());

        ArgumentsContext arguments = ctx.getArgumentsContext();

        arguments.setArgument(classService);

        try {
            pipelineManager.runPipeline(BEAN_CONSOLE_PIPELINE, ctx);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

}
