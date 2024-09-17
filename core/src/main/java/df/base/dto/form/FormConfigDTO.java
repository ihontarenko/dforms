package df.base.dto.form;

import df.base.common.support.jpa.JpaCriteria;
import df.base.dto.NestedKeyValue;
import df.base.persistence.entity.form.Form;
import df.base.persistence.entity.form.FormConfig;
import df.base.common.validation.jakarta.Fields;
import df.base.common.validation.jakarta.constraint.JpaResource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import static df.base.common.validation.jakarta.Fields.ValueType.FIELD_NAME;

// todo: check on redundant validators
@JpaResource.List({
        // validation for creating new config
        @JpaResource(
                pointer = "configName",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "configName", type = FIELD_NAME),
                                entityField = "configName"),
                        @Fields(
                                objectValue = @Fields.Value(value = "formId", type = FIELD_NAME),
                                entityField = "form.id"),

                },
                entityClass = FormConfig.class,
                message = "[NEW]: configurations with this key already taken",
                applier = "!#hasText(id)"
        ),
        // validation for updating existing config
        @JpaResource(
                pointer = "configName",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "configName", type = FIELD_NAME),
                                entityField = "configName"),
                        @Fields(
                                objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                entityField = "id",
                                comparison = JpaCriteria.Comparison.NOT_EQUAL),
                        @Fields(
                                objectValue = @Fields.Value(value = "formId", type = FIELD_NAME),
                                entityField = "form.id"),
                },
                entityClass = FormConfig.class,
                message = "[UPD]: configurations with this key already taken for this form",
                applier = "#hasText(id)"
        ),
        @JpaResource(
                pointer = "formId",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "formId", type = FIELD_NAME),
                                entityField = "id")
                },
                entityClass = Form.class,
                message = "[NEW]: the ID of the requested form does not exist",
                applier = "#hasText(formId) && !#hasText(id)",
                predicate = "!#result.empty"
        ),
        @JpaResource(
                pointer = "formId",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "formId", type = FIELD_NAME),
                                entityField = "form.id"),
                        @Fields(
                                objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                entityField = "id")
                },
                entityClass = FormConfig.class,
                message = "[UPD]: the current configurations does not belong to the requested form ID",
                applier = "#hasText(id) && #hasText(formId)",
                predicate = "!#result.empty"
        ),
        // check if request config id is correct
        @JpaResource(
                pointer = "id",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                entityField = "id")
                },
                entityClass = FormConfig.class,
                message = "unable to update configurations for non-existent entry",
                applier = "#hasText(id)",
                predicate = "!#result.empty"
        ),
})
public class FormConfigDTO implements NestedKeyValue {

    private String id;

    @NotEmpty
    @Size(max = 32)
    private String formId;

    @NotEmpty
    @Size(max = 255)
    private String configName;

    @NotEmpty
    @Size(max = 1000)
    private String configValue;

    public String id() {
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

    @Override
    public String getPrimaryId() {
        return getFormId();
    }

    @Override
    public String getKey() {
        return getConfigName();
    }

    @Override
    public String getValue() {
        return getConfigValue();
    }
}
