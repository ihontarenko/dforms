package df.base.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class FormFieldEntryDTO {

    @NotEmpty
    @Size(max = 32)
    private String id;

    @NotEmpty
    @Size(max = 32)
    private String formEntryId;

    @NotEmpty
    @Size(max = 32)
    private String formFieldId;

    @NotEmpty
    @Size(max = 1000)
    private String value;

    public String getId() {
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

    public String getFormFieldId() {
        return formFieldId;
    }

    public void setFormFieldId(String formFieldId) {
        this.formFieldId = formFieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

