package df.base.pipeline.form;

import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.DefaultPipelineContext;
import df.base.common.pipeline.context.PipelineArguments;
import df.base.service.form.FormConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormPostPersistenceProcessor implements PipelineProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormPostPersistenceProcessor.class);

    @Override
    public Enum<?> process(PipelineContext context, PipelineArguments arguments) throws Exception {
        FormConfigService configService = arguments.getArgument(FormConfigService.class);

        configService.createDefaultConfigs(((DefaultPipelineContext)context).getReturnValue());

        LOGGER.info("FORM POST-PERSISTENCE PROCESS. DEFAULT CONFIGS CREATED");

        return FormReturnCode.END;
    }

}
