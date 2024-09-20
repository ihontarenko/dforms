package df.base.common.validation.custom;

import df.base.common.context.AbstractAttributesContext;
import df.base.common.context.AttributesContext;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public interface ValidationContext extends AttributesContext {

    String BINDING_RESULT_KEY     = BindingResult.MODEL_KEY_PREFIX + ValidationContext.class.getSimpleName();
    String VALIDATION_MANAGER_KEY = "VALIDATION_MANAGER_KEY";

    class Simple extends AbstractAttributesContext implements ValidationContext {

    }

}
