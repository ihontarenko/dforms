package df.base.dto.form;

import df.base.common.support.jpa.JpaCriteria;
import df.base.common.validation.jakarta.Fields;
import df.base.common.validation.jakarta.constraint.JpaResource;
import df.base.dto.KeyValuePair;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldAttribute;
import df.base.validation.groups.Operations;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import df.base.dto.DTO;

import static df.base.common.validation.jakarta.Fields.ValueType.FIELD_NAME;

@JpaResource.List({
        // validation for creating new attribute
        @JpaResource(
                pointer = "key",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "key", type = FIELD_NAME),
                                entityField = "name"),
                        @Fields(
                                objectValue = @Fields.Value(value = "primaryId", type = FIELD_NAME),
                                entityField = "field.id"),
                },
                entityClass = FieldAttribute.class,
                message = "[NEW]: attribute with this key already taken",
                applier = "!#hasText(id)",
                groups = Operations.Secondary.class
        ),
        // validation for updating existed attribute
        @JpaResource(
                pointer = "key",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "key", type = FIELD_NAME),
                                entityField = "name"),
                        @Fields(
                                objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                entityField = "id",
                                comparison = JpaCriteria.Comparison.NOT_EQUAL),
                        @Fields(
                                objectValue = @Fields.Value(value = "primaryId", type = FIELD_NAME),
                                entityField = "field.id"),
                },
                entityClass = FieldAttribute.class,
                message = "[UPD]: attribute with this key already taken",
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
                entityClass = FieldAttribute.class,
                message = "[UPD]: the current attribute does not belong to the requested field",
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
                entityClass = FieldAttribute.class,
                message = "unable to update attribute for non-existent entry",
                applier = "#hasText(id)",
                predicate = "!#result.empty"
        ),
})
public class FieldAttributeDTO implements DTO, KeyValuePair {

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

