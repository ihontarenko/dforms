package df.base.dto.form.support;

import df.base.common.context.provider.data.EnumDataProvider;

public class PackageDataProvider extends EnumDataProvider<Package> {

    public PackageDataProvider() {
        super(Package.class, Package::getCodeWithName);
    }

}
