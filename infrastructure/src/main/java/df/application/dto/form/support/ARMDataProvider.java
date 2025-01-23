package df.application.dto.form.support;

import svit.support.provider.data.EnumDataProvider;

public class ARMDataProvider extends EnumDataProvider<ARM> {

    public ARMDataProvider() {
        super(ARM.class, ARM::getName);
    }

}
