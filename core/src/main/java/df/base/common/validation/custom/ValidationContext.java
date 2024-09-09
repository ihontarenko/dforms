package df.base.common.validation.custom;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public interface ValidationContext {

    String BINDING_RESULT_KEY     = BindingResult.MODEL_KEY_PREFIX + ValidationContext.class.getSimpleName();
    String VALIDATION_MANAGER_KEY = "VALIDATION_MANAGER_KEY";

    <T> T getAttribute(Object key);

    void setAttribute(Object key, Object value);

    class Simple implements ValidationContext {

        private final Map<Object, Object> attributes = new HashMap<>();

        @Override
        public <T> T getAttribute(Object key) {
            return (T) attributes.get(key);
        }

        @Override
        public void setAttribute(Object key, Object value) {
            attributes.put(key, value);
        }

    }

}
