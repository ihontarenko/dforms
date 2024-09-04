package df.base.persistence.support;

public class EntityGraphConstants {

    // User Graph
    public static final String USER_WITH_ROLES     = "User.withRoleOnly";
    public static final String ROLE_WITH_PRIVILEGE = "Role.withPrivilege";

    // Form Graph
    public static final String FORM_WITH_USER                       = "Form.withUserOnly";
    public static final String FORM_WITH_ENTRIES_WITH_FIELD_ENTRIES = "FormEntries.withFieldEntries";
    public static final String FORM_WITH_FIELD_ENTRIES_FIELD        = "FormFieldEntries.withField";
    public static final String FORM_CONFIG_WITH_FORM                = "FormConfig.withFormOnly";

    // Field Graph
    public static final String FORM_FIELD_WITH_CONFIGS      = "FormFieldConfig.withRelatedConfig";
    public static final String FORM_FIELD_WITH_ALL_RELATED  = "FormFieldConfig.withAllRelated";

    // Field-Config Graph
    public static final String FORM_FIELD_CONFIG_WITH_FIELD = "FormFieldConfig.withFieldOnly";

    private EntityGraphConstants() {
    }

}
