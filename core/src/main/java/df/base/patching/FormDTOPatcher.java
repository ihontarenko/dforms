package df.base.patching;

import df.base.common.Patcher;
import df.base.dto.form.FormDTO;

public class FormDTOPatcher implements Patcher<FormDTO, FormDTO, FormDTO> {

    @Override
    public FormDTO patch(FormDTO actual, FormDTO patch) {
        return actual;
    }

}
