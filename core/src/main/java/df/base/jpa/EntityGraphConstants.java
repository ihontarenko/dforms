package df.base.jpa;

public class EntityGraphConstants {

    // User Graph
    public static final String USER_WITH_ROLES                      = "User.withRoleOnly";
    public static final String ROLE_WITH_PRIVILEGE                  = "Role.withPrivilege";
    // Form Graph
    public static final String FORM_WITH_USER                       = "Form.withUserOnly";
    public static final String FORM_WITH_ENTRIES_WITH_FIELD_ENTRIES = "FormEntries.withFieldEntries";
    public static final String FORM_WITH_FIELD_ENTRIES_FIELD        = "FormFieldEntries.withField";

    private EntityGraphConstants() {
    }

}
