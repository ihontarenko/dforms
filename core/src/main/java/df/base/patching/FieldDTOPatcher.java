package df.base.patching;

import df.base.common.support.Patcher;
import df.base.dto.form.FieldDTO;

public class FieldDTOPatcher implements Patcher<FieldDTO, FieldDTO, FieldDTO> {

    @Override
    public FieldDTO patch(FieldDTO actual, FieldDTO patch) {
        return actual;
    }

}
