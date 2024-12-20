package df.base.dto.reflection;

public class ClassDTO {

    private boolean      isArray = false;
    private PackageDTO   packageDTO;
    private String       shortName;
    private String       fullName;
    private Class<?>     nativeClass;
    private MethodSetDTO methods = new MethodSetDTO();
    private FieldSetDTO  fields  = new FieldSetDTO();

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

    @Override
    public String toString() {
        return "ClassDTO: [isArray=%s, package=%s, name='%s', methods=%d, fields=%d]"
                .formatted(isArray, packageDTO.getName(), fullName, methods.size(), fields.size());
    }
}
