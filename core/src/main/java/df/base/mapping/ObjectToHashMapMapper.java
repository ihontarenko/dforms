package df.base.mapping;

import df.base.common.support.Mapper;
import df.base.common.libs.jbm.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ObjectToHashMapMapper implements Mapper<Object, Map<String, Object>> {

    @Override
    public Map<String, Object> map(Object source) {
        Map<String, Object> data = new HashMap<>();

        map(source, data);

        return data;
    }

    @Override
    public Object reverse(Map<String, Object> source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void map(Object source, Map<String, Object> destination) {
        for (Field field : ReflectionUtils.getObjectFields(source, Modifier.PRIVATE)) {
            destination.put(field.getName(), ReflectionUtils.getFieldValue(source, field));
        }
    }

    @Override
    public void reverse(Map<String, Object> source, Object destination) {
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            ReflectionUtils.setFieldValue(destination, entry.getKey(), entry.getValue());
        }
    }

}
