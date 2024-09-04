package df.base.jpa;

@SuppressWarnings({"unused"})
public final class EntityConstants {

    private EntityConstants() { }

    // Entities
    public static final String ENTITY_FORM_FIELD = "Fields";
    public static final String ENTITY_FIELD_ATTRIBUTES = "FieldAttribute";
    public static final String ENTITY_FIELD_CONFIG = "FieldConfig";
    public static final String ENTITY_FIELD_DEFAULT_VALUES = "FieldDefaultValue";
    public static final String ENTITY_FIELD_ENTRIES = "FieldEntry";
    public static final String ENTITY_FIELD_OPTIONS = "FieldOption";
    public static final String ENTITY_FORMS = "Form";
    public static final String ENTITY_FORM_CONFIG = "FormConfig";
    public static final String ENTITY_FORM_ENTRIES = "FormEntry";

    // Tables
    public static final String TABLE_FORMS = "DF_FORMS";
    public static final String TABLE_FORM_CONFIG = "DF_FORM_CONFIG";
    public static final String TABLE_FORM_FIELD_MAPPING = "DF_FORM_FIELD_MAPPING";

    public static final String TABLE_FIELDS = "DF_FIELDS";
    public static final String TABLE_FIELD_SELF_MAPPING = "DF_FIELD_SELF_MAPPING";
    public static final String TABLE_FIELD_DEFAULT_VALUES = "DF_FIELD_DEFAULT_VALUES";
    public static final String TABLE_FIELD_OPTIONS = "DF_FIELD_OPTIONS";
    public static final String TABLE_FIELD_ATTRIBUTES = "DF_FIELD_ATTRIBUTES";
    public static final String TABLE_FIELD_CONFIG = "DF_FIELD_CONFIG";

    public static final String TABLE_FORM_ENTRIES = "DF_FORM_ENTRIES";
    public static final String TABLE_FIELD_ENTRIES = "DF_FIELD_ENTRIES";

    // Forms, FORMS
    public static final String FIELD_FORMS_ID = "id";
    public static final String COLUMN_FORMS_ID = "ID";

    public static final String FIELD_FORMS_OWNER_ID = "ownerId";
    public static final String COLUMN_FORMS_OWNER_ID = "OWNER_ID";

    public static final String FIELD_FORMS_NAME = "name";
    public static final String COLUMN_FORMS_NAME = "NAME";

    public static final String FIELD_FORMS_DESCRIPTION = "description";
    public static final String COLUMN_FORMS_DESCRIPTION = "DESCRIPTION";

    public static final String FIELD_FORMS_STATUS = "status";
    public static final String COLUMN_FORMS_STATUS = "STATUS";

    public static final String FIELD_FORMS_CREATED_AT = "createdAt";
    public static final String COLUMN_FORMS_CREATED_AT = "CREATED_AT";

    // FormConfig, FORM_CONFIG
    public static final String FIELD_FORM_CONFIG_ID = "id";
    public static final String COLUMN_FORM_CONFIG_ID = "ID";

    public static final String FIELD_FORM_CONFIG_FORM_ID = "formId";
    public static final String COLUMN_FORM_CONFIG_FORM_ID = "FORM_ID";

    public static final String FIELD_FORM_CONFIG_CONFIG_NAME = "configName";
    public static final String COLUMN_FORM_CONFIG_CONFIG_NAME = "CONFIG_NAME";

    public static final String FIELD_FORM_CONFIG_CONFIG_VALUE = "configValue";
    public static final String COLUMN_FORM_CONFIG_CONFIG_VALUE = "CONFIG_VALUE";

    // FormFields, FORM_FIELDS
    public static final String FIELD_FIELD_ID = "id";
    public static final String COLUMN_FIELD_ID = "ID";

    public static final String FIELD_FIELD_ELEMENT_TYPE = "elementType";
    public static final String COLUMN_FIELD_ELEMENT_TYPE = "ELEMENT_TYPE";

    public static final String FIELD_FIELD_USAGE_TYPE = "usageType";
    public static final String COLUMN_FIELD_USAGE_TYPE = "USAGE_TYPE";

    public static final String FIELD_FIELD_NAME = "name";
    public static final String COLUMN_FIELD_NAME = "NAME";

    public static final String FIELD_FIELD_LABEL = "label";
    public static final String COLUMN_FIELD_LABEL = "LABEL";

    public static final String FIELD_FIELD_DESCRIPTION = "description";
    public static final String COLUMN_FIELD_DESCRIPTION = "DESCRIPTION";

    public static final String FIELD_FIELD_STATUS = "status";
    public static final String COLUMN_FIELD_STATUS = "STATUS";

    // FormFieldMapping, FORM_FIELD_MAPPING
    public static final String FIELD_FORM_FIELD_MAPPING_FORM_ID = "formId";
    public static final String COLUMN_FORM_FIELD_MAPPING_FORM_ID = "FORM_ID";

    public static final String FIELD_FORM_FIELD_MAPPING_FIELD_ID = "fieldId";
    public static final String COLUMN_FORM_FIELD_MAPPING_FIELD_ID = "FIELD_ID";

    // FormFieldMapping, FORM_FIELD_MAPPING
    public static final String FIELD_FIELD_SELF_MAPPING_PARENT = "parent";
    public static final String COLUMN_FIELD_SELF_MAPPING_P_ID = "P_ID";

    public static final String FIELD_FIELD_SELF_MAPPING_CHILD = "child";
    public static final String COLUMN_FIELD_SELF_MAPPING_C_ID = "C_ID";

    // FormFieldDefaultValues, FORM_FIELD_DEFAULT_VALUES
    public static final String FIELD_FIELD_DEFAULT_VALUES_ID = "id";
    public static final String COLUMN_FIELD_DEFAULT_VALUES_ID = "ID";

    public static final String FIELD_FIELD_DEFAULT_VALUES_FORM     = "form";
    public static final String COLUMN_FIELD_DEFAULT_VALUES_FORM_ID = "FORM_ID";

    public static final String FIELD_FIELD_DEFAULT_VALUES_FIELD     = "field";
    public static final String COLUMN_FIELD_DEFAULT_VALUES_FIELD_ID = "FIELD_ID";

    public static final String FIELD_FIELD_DEFAULT_VALUES_DEFAULT_VALUE = "defaultValue";
    public static final String COLUMN_FIELD_DEFAULT_VALUES_DEFAULT_VALUE = "DEFAULT_VALUE";

    // FormFieldOptions, FORM_FIELD_OPTIONS
    public static final String FIELD_FIELD_OPTIONS_ID = "id";
    public static final String COLUMN_FIELD_OPTIONS_ID = "ID";

    public static final String FIELD_FIELD_OPTIONS_FIELD_ID = "field";
    public static final String COLUMN_FIELD_OPTIONS_FIELD_ID = "FIELD_ID";

    public static final String FIELD_FIELD_OPTIONS_OPTION_VALUE = "optionValue";
    public static final String COLUMN_FIELD_OPTIONS_OPTION_VALUE = "OPTION_VALUE";

    public static final String FIELD_FIELD_OPTIONS_OPTION_LABEL = "optionLabel";
    public static final String COLUMN_FIELD_OPTIONS_OPTION_LABEL = "OPTION_LABEL";

    // FormFieldAttributes, FORM_FIELD_ATTRIBUTES
    public static final String FIELD_FIELD_ATTRIBUTES_ID = "id";
    public static final String COLUMN_FIELD_ATTRIBUTES_ID = "ID";

    public static final String FIELD_FIELD_ATTRIBUTES_FIELD     = "field";
    public static final String COLUMN_FIELD_ATTRIBUTES_FIELD_ID = "FIELD_ID";

    public static final String FIELD_FIELD_ATTRIBUTES_NAME = "name";
    public static final String COLUMN_FIELD_ATTRIBUTES_NAME = "NAME";

    public static final String FIELD_FIELD_ATTRIBUTES_VALUE = "value";
    public static final String COLUMN_FIELD_ATTRIBUTES_VALUE = "VALUE";

    // FormFieldConfig, FORM_FIELD_CONFIG
    public static final String FIELD_FIELD_CONFIG_ID = "id";
    public static final String COLUMN_FIELD_CONFIG_ID = "ID";

    public static final String FIELD_FIELD_CONFIG_FIELD     = "field";
    public static final String COLUMN_FIELD_CONFIG_FIELD_ID = "FIELD_ID";

    public static final String FIELD_FIELD_CONFIG_CONFIG_NAME = "configName";
    public static final String COLUMN_FIELD_CONFIG_CONFIG_NAME = "CONFIG_NAME";

    public static final String FIELD_FIELD_CONFIG_CONFIG_VALUE = "configValue";
    public static final String COLUMN_FIELD_CONFIG_CONFIG_VALUE = "CONFIG_VALUE";

    // FormEntries, FORM_ENTRIES
    public static final String FIELD_FORM_ENTRIES_ID = "id";
    public static final String COLUMN_FORM_ENTRIES_ID = "ID";

    public static final String FIELD_FORM_ENTRIES_FORM_ID = "form";
    public static final String COLUMN_FORM_ENTRIES_FORM_ID = "FORM_ID";

    public static final String FIELD_FORM_ENTRIES_CREATED_AT = "createdAt";
    public static final String COLUMN_FORM_ENTRIES_CREATED_AT = "CREATED_AT";

    // FormFieldEntries, FORM_FIELD_ENTRIES
    public static final String FIELD_FIELD_ENTRIES_ID = "id";
    public static final String COLUMN_FIELD_ENTRIES_ID = "ID";

    public static final String FIELD_FIELD_ENTRIES_FORM_ENTRY_ID = "formEntry";
    public static final String COLUMN_FIELD_ENTRIES_FORM_ENTRY_ID = "FORM_ENTRY_ID";

    public static final String FIELD_FIELD_ENTRIES_FIELD_ID = "field";
    public static final String COLUMN_FIELD_ENTRIES_FIELD_ID = "FIELD_ID";

    public static final String FIELD_FIELD_ENTRIES_VALUE = "value";
    public static final String COLUMN_FIELD_ENTRIES_VALUE = "VALUE";

}
