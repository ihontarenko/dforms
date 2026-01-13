package df.application.service.form;

import org.jmouse.common.pipeline.context.DefaultPipelineContext;
import org.springframework.util.MultiValueMap;

public interface FormManagement {
    void performDynamicForm(DefaultPipelineContext ctx, String primaryId, MultiValueMap<String, String> postData);

    void renderDynamicForm(DefaultPipelineContext ctx, String primaryId);
}
