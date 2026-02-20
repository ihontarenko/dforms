package df.application.pipeline.form.performing;

import org.jmouse.pipeline.PipelineResult;
import org.jmouse.common.support.objects.BeanObjectInfo;
import org.jmouse.common.support.objects.BeanObjectInfoFactory;
import org.jmouse.pipeline.PipelineProcessor;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.core.context.mutable.MutableArgumentsContext;
import org.jmouse.validator.old.Validation;
import org.jmouse.validator.old.ValidationContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;
import java.util.Map;

public class ValidateDynamicFormProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous) throws Exception {
        BindingResult       bindingResult     = new MapBindingResult(new HashMap<>(), "dynamicForm");
        Map<String, Object> requestData       = arguments.getArgument("REQUEST_DATA");
        ValidationContext   validationContext = new ValidationContext.Simple();
        Validation          validation        = context.getValue(Validation.class);
        BeanObjectInfo      beanInfo          = BeanObjectInfoFactory.createBeanObjectInfo(requestData);

        // validation.validate(beanInfo, requestData.keySet(), validationContext, bindingResult);

        context.setValue(BindingResult.class, bindingResult);

        return PipelineResult.of(
                bindingResult.hasFieldErrors() ? ReturnCodes.VALIDATION_FAIL : ReturnCodes.VALIDATION_PASS
        );
    }

}
