package df.application.dto.form.support;

import org.jmouse.core.access.data.EnumDataProvider;

public class InterfaceTypeDataProvider extends EnumDataProvider<InterfaceType> {

    public InterfaceTypeDataProvider() {
        super(InterfaceType.class, InterfaceType::getName);
    }

}
