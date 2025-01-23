package df.common.validation.custom;

import svit.support.context.AbstractAttributesContext;
import svit.support.context.AttributesContext;
import org.springframework.validation.BindingResult;

public interface ValidationContext extends AttributesContext {

    String BINDING_RESULT_KEY     = BindingResult.MODEL_KEY_PREFIX + ValidationContext.class.getSimpleName();
    String VALIDATION_MANAGER_KEY = "VALIDATION_MANAGER_KEY";
    String POINTER_KEY            = "POINTER_KEY";

    default void setPointer(String pointer) {
        setAttribute(POINTER_KEY, pointer);
    }

    default String getPointer() {
        return getAttribute(POINTER_KEY);
    }

    class Simple extends AbstractAttributesContext implements ValidationContext {

    }

}
