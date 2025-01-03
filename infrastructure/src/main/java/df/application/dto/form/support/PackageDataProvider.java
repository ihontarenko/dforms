package df.application.dto.form.support;

import df.common.context.provider.data.EnumDataProvider;

public class PackageDataProvider extends EnumDataProvider<Package> {

    public PackageDataProvider() {
        super(Package.class, Package::getCodeWithName);
    }

}
