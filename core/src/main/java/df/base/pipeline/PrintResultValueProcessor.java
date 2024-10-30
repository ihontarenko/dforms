package df.base.pipeline;

import df.base.common.pipeline.context.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.context.ArgumentsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static df.base.pipeline.form.rendering.FormRenderReturnCode.END;

public class PrintResultValueProcessor implements PipelineProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintResultValueProcessor.class);

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Object resultValue = context.getResultContext().getReturnValue();

        LOGGER.info(Objects.toString(resultValue));

        return END;
    }

}
