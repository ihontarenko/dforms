package df.application.pipeline.form.performing;

import org.jmouse.common.support.objects.BeanObjectInfo;
import org.jmouse.common.support.objects.BeanObjectInfoFactory;
import org.jmouse.common.support.context.ArgumentsContext;
import df.common.pipeline.PipelineProcessor;
import df.common.pipeline.context.PipelineContext;
import org.jmouse.validator.old.Validation;
import org.jmouse.validator.old.ValidationContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;
import java.util.Map;

public class ValidateDynamicFormProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        BindingResult       bindingResult     = new MapBindingResult(new HashMap<>(), "dynamicForm");
        Map<String, Object>                        requestData       = arguments.getArgument("REQUEST_DATA");
        org.jmouse.validator.old.ValidationContext validationContext = new ValidationContext.Simple();
        Validation                                 validation        = context.getProperty(Validation.class);
        BeanObjectInfo      beanInfo          = BeanObjectInfoFactory.createBeanObjectInfo(requestData);

        // validation.validate(beanInfo, requestData.keySet(), validationContext, bindingResult);

        context.setProperty(BindingResult.class, bindingResult);

        return bindingResult.hasFieldErrors() ? ReturnCodes.VALIDATION_FAIL : ReturnCodes.VALIDATION_PASS;
    }

}
