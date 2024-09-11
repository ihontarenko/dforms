package df.base.dto.form.support;

import df.base.common.data_provider.privider.EnumDataProvider;

public class ARMDataProvider extends EnumDataProvider<ARM> {

    public ARMDataProvider() {
        super(ARM.class, ARM::getName);
    }

}
