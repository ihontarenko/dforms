package df.application.dto.form.support;

import df.common.context.provider.data.EnumDataProvider;

public class ARMDataProvider extends EnumDataProvider<ARM> {

    public ARMDataProvider() {
        super(ARM.class, ARM::getName);
    }

}
