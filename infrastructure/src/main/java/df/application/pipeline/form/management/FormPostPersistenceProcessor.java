package df.application.pipeline.form.management;

import df.application.service.form.FormConfigService;
import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.core.context.ArgumentsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormPostPersistenceProcessor implements PipelineProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormPostPersistenceProcessor.class);

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        FormConfigService configService = arguments.getArgument(FormConfigService.class);

        configService.createDefaultConfigs(context.getResultContext().getReturnValue());

        LOGGER.info("FORM POST-PERSISTENCE PROCESS. DEFAULT CONFIGS CREATED");

        return FormReturnCode.END;
    }

}
