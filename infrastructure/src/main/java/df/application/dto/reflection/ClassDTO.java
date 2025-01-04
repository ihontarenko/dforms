package df.application.dto.reflection;

public class ClassDTO {

    private Class<?>     nativeClass;
    private String       name;
    private String       fullName;
    private boolean      isArray     = false;
    private boolean      isPrimitive = false;
    private boolean      isForeign   = false;
    private boolean      isJdk       = false;
    private ClassListDTO baseClasses = new ClassListDTO();
    private ClassListDTO  interfaces = new ClassListDTO();
    private MethodListDTO methods    = new MethodListDTO();
    private FieldListDTO  fields     = new FieldListDTO();

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getNativeClass() {
        return nativeClass;
    }

    public void setNativeClass(Class<?> nativeClass) {
        this.nativeClass = nativeClass;
    }

    public FieldListDTO getFields() {
        return fields;
    }

    public void setFields(FieldListDTO fields) {
        this.fields = fields;
    }

    public void addField(FieldDTO field) {
        this.fields.add(field);
    }

    public MethodListDTO getMethods() {
        return methods;
    }

    public void setMethods(MethodListDTO methods) {
        this.methods = methods;
    }

    public void addMethod(MethodDTO method) {
        this.methods.add(method);
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        this.isArray = array;
    }

    public boolean isForeign() {
        return isForeign;
    }

    public void setForeign(boolean foreign) {
        isForeign = foreign;
    }

    public boolean isJdk() {
        return isJdk;
    }

    public void setJdk(boolean jdk) {
        isJdk = jdk;
    }

    public boolean isPrimitive() {
        return isPrimitive;
    }

    public void setPrimitive(boolean primitive) {
        isPrimitive = primitive;
    }

    public ClassListDTO getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(ClassListDTO interfaces) {
        this.interfaces = interfaces;
    }

    public void addInterface(ClassDTO iface) {
        getInterfaces().add(iface);
    }

    public ClassListDTO getBaseClasses() {
        return baseClasses;
    }

    public void setBaseClasses(ClassListDTO baseClasses) {
        this.baseClasses = baseClasses;
    }

    public void addBaseClass(ClassDTO clazz) {
        getBaseClasses().add(clazz);
    }

    @Override
    public String toString() {
        return "ClassDTO[name='%s', isArray=%s, isPrimitive=%s, isForeign=%s, isJdk=%s]"
                .formatted(fullName, isArray, isPrimitive, isForeign, isJdk);
    }
}
