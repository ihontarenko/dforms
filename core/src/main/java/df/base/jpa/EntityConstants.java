package df.base.jpa;

@SuppressWarnings({"unused"})
public final class EntityConstants {

    private EntityConstants() { }

    // Entities
    public static final String ENTITY_FORMS = "Forms";
    public static final String ENTITY_FORM_CONFIG = "FormConfig";
    public static final String ENTITY_FORM_FIELDS = "FormFields";
    public static final String ENTITY_FORM_FIELD_GROUP = "FormFieldGroup";
    public static final String ENTITY_FORM_FIELD_MAPPING = "FormFieldMapping";
    public static final String ENTITY_FORM_FIELD_DEFAULT_VALUES = "FormFieldDefaultValues";
    public static final String ENTITY_FORM_FIELD_OPTIONS = "FormFieldOptions";
    public static final String ENTITY_FORM_FIELD_ATTRIBUTES = "FormFieldAttributes";
    public static final String ENTITY_FORM_FIELD_CONFIG = "FormFieldConfig";
    public static final String ENTITY_FORM_ENTRIES = "FormEntries";
    public static final String ENTITY_FORM_FIELD_ENTRIES = "FormFieldEntries";

    // Tables
    public static final String TABLE_FORMS = "DF_FORMS";
    public static final String TABLE_FORM_CONFIG = "DF_FORM_CONFIG";
    public static final String TABLE_FORM_FIELDS = "DF_FORM_FIELDS";
    public static final String TABLE_FORM_FIELD_GROUP = "DF_FORM_FIELD_GROUP";
    public static final String TABLE_FORM_FIELD_MAPPING = "DF_FORM_FIELD_MAPPING";
    public static final String TABLE_FORM_FIELD_DEFAULT_VALUES = "DF_FORM_FIELD_DEFAULT_VALUES";
    public static final String TABLE_FORM_FIELD_OPTIONS = "DF_FORM_FIELD_OPTIONS";
    public static final String TABLE_FORM_FIELD_ATTRIBUTES = "DF_FORM_FIELD_ATTRIBUTES";
    public static final String TABLE_FORM_FIELD_CONFIG = "DF_FORM_FIELD_CONFIG";
    public static final String TABLE_FORM_ENTRIES = "DF_FORM_ENTRIES";
    public static final String TABLE_FORM_FIELD_ENTRIES = "DF_FORM_FIELD_ENTRIES";

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
    public static final String FIELD_FORM_FIELDS_ID = "id";
    public static final String COLUMN_FORM_FIELDS_ID = "ID";

    public static final String FIELD_FORM_FIELDS_ELEMENT_TYPE = "elementType";
    public static final String COLUMN_FORM_FIELDS_ELEMENT_TYPE = "ELEMENT_TYPE";

    public static final String FIELD_FORM_FIELDS_NAME = "name";
    public static final String COLUMN_FORM_FIELDS_NAME = "NAME";

    public static final String FIELD_FORM_FIELDS_LABEL = "label";
    public static final String COLUMN_FORM_FIELDS_LABEL = "LABEL";

    public static final String FIELD_FORM_FIELDS_DESCRIPTION = "description";
    public static final String COLUMN_FORM_FIELDS_DESCRIPTION = "DESCRIPTION";

    public static final String FIELD_FORM_FIELDS_STATUS = "status";
    public static final String COLUMN_FORM_FIELDS_STATUS = "STATUS";

    // FormFieldGroup, FORM_FIELD_GROUP
    public static final String FIELD_FORM_FIELD_GROUP_ID = "id";
    public static final String COLUMN_FORM_FIELD_GROUP_ID = "ID";

    public static final String FIELD_FORM_FIELD_GROUP_PARENT_FIELD_ID = "parentFieldId";
    public static final String COLUMN_FORM_FIELD_GROUP_PARENT_FIELD_ID = "PARENT_FIELD_ID";

    public static final String FIELD_FORM_FIELD_GROUP_FIELD_ID = "fieldId";
    public static final String COLUMN_FORM_FIELD_GROUP_FIELD_ID = "FIELD_ID";

    public static final String FIELD_FORM_FIELD_GROUP_SEQUENCE_ORDER = "sequenceOrder";
    public static final String COLUMN_FORM_FIELD_GROUP_SEQUENCE_ORDER = "SEQUENCE_ORDER";

    // FormFieldMapping, FORM_FIELD_MAPPING
    public static final String FIELD_FORM_FIELD_MAPPING_FORM_ID = "formId";
    public static final String COLUMN_FORM_FIELD_MAPPING_FORM_ID = "FORM_ID";

    public static final String FIELD_FORM_FIELD_MAPPING_FIELD_ID = "fieldId";
    public static final String COLUMN_FORM_FIELD_MAPPING_FIELD_ID = "FIELD_ID";

    // FormFieldDefaultValues, FORM_FIELD_DEFAULT_VALUES
    public static final String FIELD_FORM_FIELD_DEFAULT_VALUES_ID = "id";
    public static final String COLUMN_FORM_FIELD_DEFAULT_VALUES_ID = "ID";

    public static final String FIELD_FORM_FIELD_DEFAULT_VALUES_FORM_ID = "formId";
    public static final String COLUMN_FORM_FIELD_DEFAULT_VALUES_FORM_ID = "FORM_ID";

    public static final String FIELD_FORM_FIELD_DEFAULT_VALUES_FIELD_ID = "fieldId";
    public static final String COLUMN_FORM_FIELD_DEFAULT_VALUES_FIELD_ID = "FIELD_ID";

    public static final String FIELD_FORM_FIELD_DEFAULT_VALUES_DEFAULT_VALUE = "defaultValue";
    public static final String COLUMN_FORM_FIELD_DEFAULT_VALUES_DEFAULT_VALUE = "DEFAULT_VALUE";

    // FormFieldOptions, FORM_FIELD_OPTIONS
    public static final String FIELD_FORM_FIELD_OPTIONS_ID = "id";
    public static final String COLUMN_FORM_FIELD_OPTIONS_ID = "ID";

    public static final String FIELD_FORM_FIELD_OPTIONS_FIELD_ID = "fieldId";
    public static final String COLUMN_FORM_FIELD_OPTIONS_FIELD_ID = "FIELD_ID";

    public static final String FIELD_FORM_FIELD_OPTIONS_OPTION_VALUE = "optionValue";
    public static final String COLUMN_FORM_FIELD_OPTIONS_OPTION_VALUE = "OPTION_VALUE";

    public static final String FIELD_FORM_FIELD_OPTIONS_OPTION_LABEL = "optionLabel";
    public static final String COLUMN_FORM_FIELD_OPTIONS_OPTION_LABEL = "OPTION_LABEL";

    // FormFieldAttributes, FORM_FIELD_ATTRIBUTES
    public static final String FIELD_FORM_FIELD_ATTRIBUTES_ID = "id";
    public static final String COLUMN_FORM_FIELD_ATTRIBUTES_ID = "ID";

    public static final String FIELD_FORM_FIELD_ATTRIBUTES_FIELD_ID = "formFieldId";
    public static final String COLUMN_FORM_FIELD_ATTRIBUTES_FIELD_ID = "FIELD_ID";

    public static final String FIELD_FORM_FIELD_ATTRIBUTES_NAME = "name";
    public static final String COLUMN_FORM_FIELD_ATTRIBUTES_NAME = "NAME";

    public static final String FIELD_FORM_FIELD_ATTRIBUTES_VALUE = "value";
    public static final String COLUMN_FORM_FIELD_ATTRIBUTES_VALUE = "VALUE";

    // FormFieldConfig, FORM_FIELD_CONFIG
    public static final String FIELD_FORM_FIELD_CONFIG_ID = "id";
    public static final String COLUMN_FORM_FIELD_CONFIG_ID = "ID";

    public static final String FIELD_FORM_FIELD_CONFIG_FIELD_ID = "formFieldId";
    public static final String COLUMN_FORM_FIELD_CONFIG_FIELD_ID = "FIELD_ID";

    public static final String FIELD_FORM_FIELD_CONFIG_CONFIG_NAME = "configName";
    public static final String COLUMN_FORM_FIELD_CONFIG_CONFIG_NAME = "CONFIG_NAME";

    public static final String FIELD_FORM_FIELD_CONFIG_CONFIG_VALUE = "configValue";
    public static final String COLUMN_FORM_FIELD_CONFIG_CONFIG_VALUE = "CONFIG_VALUE";

    // FormEntries, FORM_ENTRIES
    public static final String FIELD_FORM_ENTRIES_ID = "id";
    public static final String COLUMN_FORM_ENTRIES_ID = "ID";

    public static final String FIELD_FORM_ENTRIES_FORM_ID = "formId";
    public static final String COLUMN_FORM_ENTRIES_FORM_ID = "FORM_ID";

    public static final String FIELD_FORM_ENTRIES_CREATED_AT = "createdAt";
    public static final String COLUMN_FORM_ENTRIES_CREATED_AT = "CREATED_AT";

    // FormFieldEntries, FORM_FIELD_ENTRIES
    public static final String FIELD_FORM_FIELD_ENTRIES_ID = "id";
    public static final String COLUMN_FORM_FIELD_ENTRIES_ID = "ID";

    public static final String FIELD_FORM_FIELD_ENTRIES_FORM_ENTRY_ID = "formEntryId";
    public static final String COLUMN_FORM_FIELD_ENTRIES_FORM_ENTRY_ID = "FORM_ENTRY_ID";

    public static final String FIELD_FORM_FIELD_ENTRIES_FIELD_ID = "fieldId";
    public static final String COLUMN_FORM_FIELD_ENTRIES_FIELD_ID = "FIELD_ID";

    public static final String FIELD_FORM_FIELD_ENTRIES_VALUE = "value";
    public static final String COLUMN_FORM_FIELD_ENTRIES_VALUE = "VALUE";

}
