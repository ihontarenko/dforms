package df.base.dto.reflection;

import java.lang.reflect.Field;

public class FieldDTO {

    private String   name;
    private Field    nativeField;
    private Class<?> nativeClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getNativeField() {
        return nativeField;
    }

    public void setNativeField(Field nativeField) {
        this.nativeField = nativeField;
    }

    public Class<?> getNativeClass() {
        return nativeClass;
    }

    public void setNativeClass(Class<?> nativeClass) {
        this.nativeClass = nativeClass;
    }
}
