package df.base.pipeline.form;

import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.pipeline.DefaultReturnCode;
import df.base.service.form.FormService;

import java.util.Random;

public class FormCreateOrUpdateProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context) throws Exception {

        FormService service = context.getProperty(FormService.class);

        return new Random().nextInt() % 2 == 0 ? DefaultReturnCode.BATCH_ITEMS : DefaultReturnCode.SINGLE_ITEM;
    }

}
