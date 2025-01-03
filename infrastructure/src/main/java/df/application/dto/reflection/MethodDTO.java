package df.application.dto.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodDTO {

    private final ClassSetDTO parametersTypes = new ClassSetDTO();
    private       String      name;
    private       ClassDTO    declaringClass;
    private       ClassDTO    returnType;
    private       Method      nativeMethod;
    private       Class<?>    nativeClass;

    public ClassDTO getDeclaringClass() {
        return declaringClass;
    }

    public void setDeclaringClass(ClassDTO declaringClass) {
        this.declaringClass = declaringClass;
    }

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

    public void addParameterType(ClassDTO type) {
        this.parametersTypes.add(type);
    }

    public ClassSetDTO getParametersTypes() {
        return parametersTypes;
    }

    public ClassDTO getReturnType() {
        return returnType;
    }

    public void setReturnType(ClassDTO returnType) {
        this.returnType = returnType;
    }

    public String getAccessLevel() {
        if ((nativeMethod.getModifiers() & Modifier.PRIVATE) != 0) {
            return "PRIVATE";
        } else if ((nativeMethod.getModifiers() & Modifier.PROTECTED) != 0) {
            return "PROTECTED";
        }
        return "PUBLIC";
    }

}
