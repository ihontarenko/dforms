package df.application.dto.form.support;

import org.jmouse.common.support.provider.data.EnumDataProvider;

public class PackageDataProvider extends EnumDataProvider<Package> {

    public PackageDataProvider() {
        super(Package.class, Package::getCodeWithName);
    }

}
