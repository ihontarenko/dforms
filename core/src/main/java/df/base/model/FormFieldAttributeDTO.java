package df.base.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class FormFieldAttributeDTO {

    @NotEmpty
    @Size(max = 32)
    private String id;

    @NotEmpty
    @Size(max = 32)
    private String formFieldId;

    @NotEmpty
    @Size(max = 255)
    private String name;

    @NotEmpty
    @Size(max = 1000)
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormFieldId() {
        return formFieldId;
    }

    public void setFormFieldId(String formFieldId) {
        this.formFieldId = formFieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

