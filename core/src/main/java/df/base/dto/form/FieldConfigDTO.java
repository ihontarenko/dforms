package df.base.dto.form;

import df.base.common.support.jpa.JpaCriteria;
import df.base.common.validation.jakarta.Fields;
import df.base.common.validation.jakarta.constraint.JpaResource;
import df.base.dto.DTO;
import df.base.dto.NestedKeyValue;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldConfig;
import df.base.validation.groups.Operations;
import jakarta.validation.constraints.NotEmpty;
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
                                objectValue = @Fields.Value(value = "primaryId", type = FIELD_NAME),
                                entityField = "field.id"),
                },
                entityClass = FieldConfig.class,
                message = "[NEW]: config with this key already taken",
                applier = "!#hasText(id)",
                groups = Operations.Secondary.class
        ),
        // validation for updating existed config
        @JpaResource(
                pointer = "key",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "key", type = FIELD_NAME),
                                entityField = "configName"),
                        @Fields(
                                objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                entityField = "id",
                                comparison = JpaCriteria.Comparison.NOT_EQUAL),
                        @Fields(
                                objectValue = @Fields.Value(value = "primaryId", type = FIELD_NAME),
                                entityField = "field.id"),
                },
                entityClass = FieldConfig.class,
                message = "[UPD]: config with this key already taken",
                applier = "#hasText(id)",
                groups = Operations.Secondary.class
        ),
        // check if primaryKey is correct
        @JpaResource(
                pointer = "primaryId",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "primaryId", type = FIELD_NAME),
                                entityField = "id")
                },
                entityClass = Field.class,
                message = "[NEW]: passed field does not exist. don't modify request :)",
                applier = "#hasText(primaryId) && !#hasText(id)",
                predicate = "!#result.empty",
                groups = Operations.Secondary.class
        ),
        // check if updated item belongs to owner-id
        @JpaResource(
                pointer = "primaryId",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "primaryId", type = FIELD_NAME),
                                entityField = "field.id"),
                        @Fields(
                                objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                entityField = "id")
                },
                entityClass = FieldConfig.class,
                message = "[UPD]: the current configuration does not belong to the requested field ID",
                applier = "#hasText(id) && #hasText(primaryId)",
                predicate = "!#result.empty",
                groups = Operations.Secondary.class
        ),
        // check if request config id is correct
        @JpaResource(
                pointer = "id",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                entityField = "id")
                },
                entityClass = FieldConfig.class,
                message = "unable to update configuration for non-existent entry",
                applier = "#hasText(id)",
                predicate = "!#result.empty"
        ),
})
public class FieldConfigDTO implements DTO, NestedKeyValue {

    @Size(max = 32, groups = Operations.Secondary.class)
    private String id;

    @NotEmpty(groups = Operations.Secondary.class)
    @Size(max = 32, groups = Operations.Secondary.class)
    private String fieldId;

    @NotEmpty(groups = Operations.Secondary.class)
    @Size(max = 32, groups = Operations.Secondary.class)
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

