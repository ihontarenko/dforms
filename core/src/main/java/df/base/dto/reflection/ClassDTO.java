package df.base.dto.reflection;

public class ClassDTO {

    private boolean      isArray     = false;
    private boolean      isPrimitive = false;
    private boolean      isForeign   = false;
    private boolean      isJdk       = false;
    private String       shortName;
    private String       fullName;
    private PackageDTO   packageDTO;
    private Class<?>     nativeClass;
    private MethodSetDTO methods     = new MethodSetDTO();
    private FieldSetDTO  fields      = new FieldSetDTO();

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Class<?> getNativeClass() {
        return nativeClass;
    }

    public void setNativeClass(Class<?> nativeClass) {
        this.nativeClass = nativeClass;
    }

    public FieldSetDTO getFields() {
        return fields;
    }

    public void setFields(FieldSetDTO fields) {
        this.fields = fields;
    }

    public void addField(FieldDTO field) {
        this.fields.add(field);
    }

    public MethodSetDTO getMethods() {
        return methods;
    }

    public void setMethods(MethodSetDTO methods) {
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

    public PackageDTO getPackage() {
        return packageDTO;
    }

    public void setPackage(PackageDTO packageDTO) {
        this.packageDTO = packageDTO;
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

    @Override
    public String toString() {
        return "ClassDTO[name='%s', isArray=%s, isPrimitive=%s, isForeign=%s, isJdk=%s]"
                .formatted(fullName, isArray, isPrimitive, isForeign, isJdk);
    }
}
