package df.base.pipeline.management.performing;

import df.base.common.context.beans.BeanObjectInfo;
import df.base.common.context.beans.BeanObjectInfoFactory;
import df.base.common.context.ArgumentsContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineContext;
import df.base.common.validation.custom.Validation;
import df.base.common.validation.custom.ValidationContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;
import java.util.Map;

public class ValidateDynamicFormProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        BindingResult       bindingResult     = new MapBindingResult(new HashMap<>(), "dynamicForm");
        Map<String, Object> requestData       = arguments.getArgument("REQUEST_DATA");
        ValidationContext   validationContext = new ValidationContext.Simple();
        Validation          validation        = context.getProperty(Validation.class);
        BeanObjectInfo      beanInfo          = BeanObjectInfoFactory.createBeanObjectInfo(requestData);

        validation.validate(beanInfo, requestData.keySet(), validationContext, bindingResult);

        context.setProperty(BindingResult.class, bindingResult);

        return bindingResult.hasFieldErrors() ? ReturnCodes.VALIDATION_FAIL : ReturnCodes.VALIDATION_PASS;
    }

}
