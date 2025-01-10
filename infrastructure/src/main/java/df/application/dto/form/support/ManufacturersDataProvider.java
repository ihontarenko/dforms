package df.application.dto.form.support;

import svit.provider.data.EnumDataProvider;

public class ManufacturersDataProvider extends EnumDataProvider<Manufacturers> {

    public ManufacturersDataProvider() {
        super(Manufacturers.class, Manufacturers::getFullName);
    }

}
