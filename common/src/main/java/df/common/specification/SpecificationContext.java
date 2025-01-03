package df.common.specification;

import java.util.HashMap;
import java.util.Map;

public interface SpecificationContext {

    <T> T getAttribute(Object attributeKey);

    void setAttribute(Object attributeKey, Object attributeValue);

    class SimpleSpecificationContext implements SpecificationContext {

        private final Map<Object, Object> attributes;

        public SimpleSpecificationContext() {
            this.attributes = new HashMap<>();
        }

        @Override
        public <T> T getAttribute(Object key) {
            return (T) attributes.get(key);
        }

        @Override
        public void setAttribute(Object key, Object value) {
            attributes.put(key, value);
        }

    }

    class Builder {

        private SpecificationContext context = new SimpleSpecificationContext();

        public Builder with(Object key, Object value) {
            context.setAttribute(key, value);
            return this;
        }

        public SpecificationContext build() {
            return context;
        }

    }

}
