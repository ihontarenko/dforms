package df.application.pipeline.form.management;

import df.application.service.form.FormConfigService;
import df.common.pipeline.context.PipelineContext;
import df.common.pipeline.PipelineProcessor;
import df.common.pipeline.context.DefaultPipelineContext;
import svit.support.context.ArgumentsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormPostPersistenceProcessor implements PipelineProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormPostPersistenceProcessor.class);

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        FormConfigService configService = arguments.getArgument(FormConfigService.class);

        configService.createDefaultConfigs(((DefaultPipelineContext)context).getReturnValue());

        LOGGER.info("FORM POST-PERSISTENCE PROCESS. DEFAULT CONFIGS CREATED");

        return FormReturnCode.END;
    }

}
