package df.application.dto.form.support;

import org.jmouse.core.access.data.EnumDataProvider;

public class ManufacturersDataProvider extends EnumDataProvider<Manufacturers> {

    public ManufacturersDataProvider() {
        super(Manufacturers.class, Manufacturers::getFullName);
    }

}
