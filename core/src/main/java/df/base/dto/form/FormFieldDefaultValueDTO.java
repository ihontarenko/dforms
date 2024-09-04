package df.base.dto.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class FormFieldDefaultValueDTO {

    @NotEmpty
    @Size(max = 32)
    private String id;

    @NotEmpty
    @Size(max = 32)
    private String formId;

    @NotEmpty
    @Size(max = 32)
    private String fieldId;

    @NotEmpty
    @Size(max = 1000)
    private String defaultValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

}

