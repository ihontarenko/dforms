package df.base.dto.form;

import df.base.persistence.entity.form.FieldOption;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.Map;

public class FieldNestedDTO {

    @Valid
    private Map<@Valid String, @Valid FieldOption> options;

    @Valid
    private Map<@Valid String, @Valid FieldAttributeDTO> attributes;

    @Valid
    private Map<@Valid String, @Valid FieldConfigDTO> configs;

    @NotEmpty
    private String fieldId;

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public Map<String, FieldOption> getOptions() {
        return options;
    }

    public void setOptions(Map<String, FieldOption> options) {
        this.options = options;
    }

    public Map<String, FieldAttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, FieldAttributeDTO> attributes) {
        this.attributes = attributes;
    }

    public Map<String, FieldConfigDTO> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, FieldConfigDTO> configs) {
        this.configs = configs;
    }

}
