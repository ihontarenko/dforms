package df.application.dto.form.support;

import svit.provider.data.EnumDataProvider;

public class ARMDataProvider extends EnumDataProvider<ARM> {

    public ARMDataProvider() {
        super(ARM.class, ARM::getName);
    }

}
