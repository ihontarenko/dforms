package df.base.dto.reflection;

import java.lang.reflect.Method;

public class MethodDTO {

    private String   name;
    private Method   nativeMethod;
    private Class<?> nativeClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Method getNativeMethod() {
        return nativeMethod;
    }

    public void setNativeMethod(Method nativeMethod) {
        this.nativeMethod = nativeMethod;
    }

    public Class<?> getNativeClass() {
        return nativeClass;
    }

    public void setNativeClass(Class<?> nativeClass) {
        this.nativeClass = nativeClass;
    }

}
