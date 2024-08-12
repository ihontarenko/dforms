package df.web.common.pebble.function;

import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import io.pebbletemplates.spring.extension.function.bindingresult.BaseBindingResultFunction;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;

import java.util.Map;

public class IfFieldErrors extends BaseBindingResultFunction {

    public static final  String FUNCTION_NAME       = "ifFieldErrors";
    private static final String ARGUMENT_FORM_NAME  = "formName";
    private static final String ARGUMENT_FIELD_NAME = "field";
    private static final String ARGUMENT_IF_TRUE    = "ifTrue";
    private static final String ARGUMENT_IF_FALSE   = "ifFalse";

    public IfFieldErrors() {
        super(ARGUMENT_FORM_NAME, ARGUMENT_FIELD_NAME, ARGUMENT_IF_TRUE, ARGUMENT_IF_FALSE);
    }

    @Override
    public Object execute(Map<String, Object> arguments, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        String             formName      = (String) arguments.get(ARGUMENT_FORM_NAME);
        String             ifTrue        = (String) arguments.get(ARGUMENT_IF_TRUE);
        String             ifFalse       = (String) arguments.get(ARGUMENT_IF_FALSE);
        String             fieldName     = (String) arguments.get(ARGUMENT_FIELD_NAME);
        BindingResult      bindingResult = this.getBindingResult(formName, context);
        HttpServletRequest request       = (HttpServletRequest) context.getVariable("request");

        request.getMethod();

        if (bindingResult != null && !"GET".equals(request.getMethod())) {
            return bindingResult.hasFieldErrors(fieldName) ? ifTrue : ifFalse;
        } else {
            return null;
        }
    }

}
