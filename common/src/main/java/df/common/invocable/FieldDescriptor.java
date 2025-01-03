package df.common.invocable;

import java.lang.reflect.Field;

public interface FieldDescriptor extends TypeDescriptor {

    ClassTypeDescriptor getObjectDescriptor();

    Field getNativeField();

    Class<?> getFieldType();

    ClassTypeDescriptor getFieldTypeDescriptor();

}