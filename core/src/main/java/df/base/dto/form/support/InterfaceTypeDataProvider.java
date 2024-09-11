package df.base.dto.form.support;

import df.base.common.data_provider.privider.EnumDataProvider;

public class InterfaceTypeDataProvider extends EnumDataProvider<InterfaceType> {

    public InterfaceTypeDataProvider() {
        super(InterfaceType.class, InterfaceType::getName);
    }

}
