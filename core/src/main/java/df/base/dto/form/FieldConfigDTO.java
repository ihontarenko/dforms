package df.base.dto.form;

import df.base.dto.DTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class FieldConfigDTO implements DTO {

    @NotEmpty
    @Size(max = 32)
    private String id;

    @NotEmpty
    @Size(max = 32)
    private String fieldId;

    @NotEmpty
    @Size(max = 255)
    private String configName;

    @NotEmpty
    @Size(max = 1000)
    private String configValue;

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

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}

