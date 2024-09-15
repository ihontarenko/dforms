package df.base.pipeline.form;

import df.base.common.pipeline.PipelineChain;
import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.persistence.entity.form.Form;

public class LogFormEntityProcessor implements PipelineProcessor {

    @Override
    public void process(PipelineContext context, PipelineChain chain) throws Exception {
        System.out.println("processor: " + getClass() + " -> " + context.<Form>getProperty(Form.class).getDescription());
        chain.proceed(context);
    }

}
