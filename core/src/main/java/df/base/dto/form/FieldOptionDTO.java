package df.base.dto.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class FieldOptionDTO {

    @NotEmpty
    @Size(max = 32)
    private String id;

    @NotEmpty
    @Size(max = 32)
    private String fieldId;

    @NotEmpty
    @Size(max = 255)
    private String optionValue;

    @NotEmpty
    @Size(max = 255)
    private String optionLabel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getOptionLabel() {
        return optionLabel;
    }

    public void setOptionLabel(String optionLabel) {
        this.optionLabel = optionLabel;
    }
}

