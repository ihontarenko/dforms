package df.base.dto.form;

import df.base.dto.KeyValueDTO;
import df.base.validation.groups.Operations;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import df.base.dto.DTO;

public class FieldOptionDTO implements DTO, KeyValueDTO {

    @Size(max = 32, groups = Operations.Secondary.class)
    private String id;

    @Size(max = 32, groups = Operations.Secondary.class)
    private String fieldId;

    @NotEmpty
    @Size(max = 32, groups = Operations.Secondary.class)
    private String key;

    @NotEmpty
    @Size(max = 255, groups = Operations.Secondary.class)
    private String value;

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

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getOwnerId() {
        return getFieldId();
    }

    @Override
    public void setOwnerId(String id) {
        setFieldId(id);
    }

}

