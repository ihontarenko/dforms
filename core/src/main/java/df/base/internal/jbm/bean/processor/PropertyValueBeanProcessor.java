package df.base.internal.jbm.bean.processor;

import df.base.internal.jbm.bean.EnvironmentValue;
import df.base.internal.jbm.bean.context.JbmContext;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PropertyValueBeanProcessor implements BeanProcessor {

    private final Map<Object, Object> globals;

    public PropertyValueBeanProcessor(Map<?, ?>... maps) {
        Map<Object, Object> globals = new HashMap<>();

        for (Map<?, ?> map : maps) {
            globals.putAll(map);
        }

        this.globals = globals;
    }

    @Override
    public void process(Object bean, JbmContext context) {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(EnvironmentValue.class)) {
                if (String.class.isAssignableFrom(field.getType())) {
                    EnvironmentValue variable = field.getAnnotation(EnvironmentValue.class);
                    try {
                        field.setAccessible(true);
                        field.set(bean, globals.get(variable.value()));
                    } catch (IllegalAccessException e) {
                        // no-ops
                    }
                } else {
                    throw new BeanProcessorException(
                            "FIELD ANNOTATED AS '" + EnvironmentValue.class + "' IN NOT " + String.class.getName());
                }
            }
        }
    }
}
