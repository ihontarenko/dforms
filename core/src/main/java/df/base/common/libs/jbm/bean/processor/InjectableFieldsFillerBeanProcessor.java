package df.base.common.libs.jbm.bean.processor;

import df.base.common.libs.jbm.bean.BeanFactory;
import df.base.common.libs.jbm.bean.BeanInjection;
import df.base.common.libs.jbm.bean.context.JbmContext;

import java.lang.reflect.Field;

public class InjectableFieldsFillerBeanProcessor implements BeanProcessor {

    @Override
    public void process(Object bean, JbmContext context) {
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
