package df.application.dto.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import df.application.dto.DTO;

public class FieldEntryDTO implements DTO {

    @NotEmpty
    @Size(max = 32)
    private String id;

    @NotEmpty
    @Size(max = 32)
    private String formEntryId;

    @NotEmpty
    @Size(max = 32)
    private String fieldId;

    @NotEmpty
    @Size(max = 1000)
    private String value;

    public String id() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormEntryId() {
        return formEntryId;
    }

    public void setFormEntryId(String formEntryId) {
        this.formEntryId = formEntryId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

