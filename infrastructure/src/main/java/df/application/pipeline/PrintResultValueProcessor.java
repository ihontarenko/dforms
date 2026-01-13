package df.application.pipeline;

import df.application.pipeline.form.rendering.FormRenderReturnCode;
import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.core.context.ArgumentsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class PrintResultValueProcessor implements PipelineProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintResultValueProcessor.class);

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Object resultValue = context.getResultContext().getReturnValue();

        LOGGER.info(Objects.toString(resultValue));

        return FormRenderReturnCode.END;
    }

}
