package df.application.dto.reflection;

public class PackageDTO {

    private String  name;
    private Package nativePackage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Package getNativePackage() {
        return nativePackage;
    }

    public void setNativePackage(Package nativePackage) {
        this.nativePackage = nativePackage;
    }

}
