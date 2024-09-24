package df.base.dto.form.support;

import df.base.common.context.provider.data.EnumDataProvider;

public class InterfaceTypeDataProvider extends EnumDataProvider<InterfaceType> {

    public InterfaceTypeDataProvider() {
        super(InterfaceType.class, InterfaceType::getName);
    }

}
