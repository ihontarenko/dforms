package df.base.dto.reflection;

import java.util.ArrayList;
import java.util.List;

public class ClassDTO {

    private boolean         undefinedDependencies = false;
    private String          name;
    private Class<?>        nativeClass;
    private List<MethodDTO> methods               = new ArrayList<>();
    private List<FieldDTO>  fields                = new ArrayList<>();

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

    public List<FieldDTO> getFields() {
        return fields;
    }

    public void setFields(List<FieldDTO> fields) {
        this.fields = fields;
    }

    public void addField(FieldDTO field) {
        this.fields.add(field);
    }

    public List<MethodDTO> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodDTO> methods) {
        this.methods = methods;
    }

    public void addMethod(MethodDTO method) {
        this.methods.add(method);
    }

    public boolean isUndefinedDependencies() {
        return undefinedDependencies;
    }

    public void setUndefinedDependencies(boolean undefinedDependencies) {
        this.undefinedDependencies = undefinedDependencies;
    }
}
