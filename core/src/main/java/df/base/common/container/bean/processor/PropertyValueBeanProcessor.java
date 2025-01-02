package df.base.common.container.bean.processor;

import df.base.common.container.bean.context.BeanContainerContext;
import df.base.common.container.bean.annotation.EnvironmentValue;

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
    public void process(Object bean, BeanContainerContext context) {
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
