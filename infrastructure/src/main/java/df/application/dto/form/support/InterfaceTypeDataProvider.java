package df.application.dto.form.support;

import svit.support.provider.data.EnumDataProvider;

public class InterfaceTypeDataProvider extends EnumDataProvider<InterfaceType> {

    public InterfaceTypeDataProvider() {
        super(InterfaceType.class, InterfaceType::getName);
    }

}
