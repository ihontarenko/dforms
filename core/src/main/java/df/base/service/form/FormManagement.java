package df.base.service.form;

import df.base.common.pipeline.context.DefaultPipelineContext;
import org.springframework.util.MultiValueMap;

public interface FormManagement {
    void performDynamicForm(DefaultPipelineContext ctx, String primaryId, MultiValueMap<String, String> postData);

    void renderDynamicForm(DefaultPipelineContext ctx, String primaryId);
}
