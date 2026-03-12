package df.application.dto.form.support;

import org.jmouse.core.access.data.EnumDataProvider;
import org.jmouse.geo.Country;

public class CountryDataProvider extends EnumDataProvider<Country> {

    public CountryDataProvider() {
        super(Country.class, Country::getName);
    }

}
