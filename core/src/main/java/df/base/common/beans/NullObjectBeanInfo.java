package df.base.common.beans;

import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static df.base.common.libs.jbm.ReflectionUtils.readPropertyDescriptors;

public class NullObjectBeanInfo implements BeanObjectInfo {

    @Override
    public <T> T getObject() {
        return (T) new Object();
    }

    @Override
    public Class<?> getClassType() {
        return void.class;
    }

    @Override
    public Map<String, BeanField> getBeanFields() {
        return Collections.emptyMap();
    }

    @Override
    public BeanField getBeanField(String fieldName) {
        return new NullBeanField();
    }

    @Override
    public PropertyDescriptor getPropertyDescriptor(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, PropertyDescriptor> getPropertyDescriptors() {
        throw new UnsupportedOperationException();
    }
}

