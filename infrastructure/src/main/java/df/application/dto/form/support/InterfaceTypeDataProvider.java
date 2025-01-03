package df.application.dto.form.support;

import df.common.context.provider.data.EnumDataProvider;

public class InterfaceTypeDataProvider extends EnumDataProvider<InterfaceType> {

    public InterfaceTypeDataProvider() {
        super(InterfaceType.class, InterfaceType::getName);
    }

}