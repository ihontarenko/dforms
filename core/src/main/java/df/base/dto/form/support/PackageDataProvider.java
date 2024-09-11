package df.base.dto.form.support;

import df.base.common.data_provider.privider.EnumDataProvider;

public class PackageDataProvider extends EnumDataProvider<Package> {

    public PackageDataProvider() {
        super(Package.class, Package::getCodeWithName);
    }

}
