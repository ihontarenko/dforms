package df.base.dto.form.support;

import df.base.common.context.provider.data.EnumDataProvider;

public class ManufacturersDataProvider extends EnumDataProvider<Manufacturers> {

    public ManufacturersDataProvider() {
        super(Manufacturers.class, Manufacturers::getFullName);
    }

}
