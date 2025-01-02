package df.base.common.container.bean.processor;

import df.base.common.container.bean.BeanFactory;
import df.base.common.container.bean.annotation.BeanInjection;
import df.base.common.container.bean.context.BeanContainerContext;

import java.lang.reflect.Field;

public class InjectableFieldsFillerBeanProcessor implements BeanProcessor {

    @Override
    public void process(Object bean, BeanContainerContext context) {
        BeanFactory factory = context.getFactory();
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(BeanInjection.class)) {
                String beanName = factory.getBeanName(field, BeanInjection.class);
                Object value    = factory.getBean(field.getType(), beanName);
                try {
                    field.setAccessible(true);
                    field.set(bean, value);
                } catch (IllegalAccessException e) {
                    // no-ops
                }
            }
        }
    }
}
