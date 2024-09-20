package df.base.common.beans;

import java.util.Map;

final public class BeanObjectInfoFactory {

    public static BeanObjectInfo createBeanObjectInfo(Object object) {
        BeanObjectInfo beanObjectInfo = null;

        if (object instanceof Map<?,?> map) {
            beanObjectInfo = new MapObjectBeanInfo((Map<String, Object>) map);
        } else {
            beanObjectInfo = new ObjectBeanInfo(object);
        }

        return beanObjectInfo;
    }

}
