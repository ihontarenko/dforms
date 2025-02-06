package df.application.dto.form.support;

import org.jmouse.common.support.provider.data.EnumDataProvider;

public class ManufacturersDataProvider extends EnumDataProvider<Manufacturers> {

    public ManufacturersDataProvider() {
        super(Manufacturers.class, Manufacturers::getFullName);
    }

}
