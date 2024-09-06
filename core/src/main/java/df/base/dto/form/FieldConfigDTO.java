package df.base.dto.form;

import df.base.common.validation.jakarta.Fields;
import df.base.common.validation.jakarta.constraint.JpaResource;
import df.base.dto.DTO;
import df.base.dto.SecondaryDTO;
import df.base.persistence.entity.form.FieldConfig;
import df.base.validation.groups.Operations;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static df.base.common.validation.jakarta.Fields.ValueType.FIELD_NAME;

@JpaResource.List({
        // validation for creating new config
        @JpaResource(
                pointer = "key",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "key", type = FIELD_NAME),
                                entityField = "configName"),
                        @Fields(
                                objectValue = @Fields.Value(value = "fieldId", type = FIELD_NAME),
                                entityField = "field.id"),
                },
                entityClass = FieldConfig.class,
                message = "[NEW]: configuration with this key already taken",
                applier = "!#hasText(id)",
                groups = Operations.Secondary.class
        ),
})
public class FieldConfigDTO implements DTO, SecondaryDTO {

    @Size(max = 32, groups = Operations.Secondary.class)
    private String id;

    @NotEmpty(groups = Operations.Secondary.class)
    @Size(max = 32, groups = Operations.Secondary.class)
    private String fieldId;

    @NotEmpty(groups = Operations.Secondary.class)
    @Size(max = 32, groups = Operations.Secondary.class)
    @Pattern(regexp = "[0-9A-Z_]+", groups = Operations.Secondary.class)
    private String key;

    @NotEmpty(groups = Operations.Secondary.class)
    @Size(max = 255, groups = Operations.Secondary.class)
    private String value;

    public String id() {
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

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getPrimaryId() {
        return getFieldId();
    }

    public void setPrimaryId(String id) {
        setFieldId(id);
    }
}

