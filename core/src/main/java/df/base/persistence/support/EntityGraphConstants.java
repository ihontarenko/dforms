package df.base.persistence.support;

public class EntityGraphConstants {

    // User Graph
    public static final String USER_WITH_ROLES     = "User:role";
    public static final String ROLE_WITH_PRIVILEGE = "Role:privilege";

    // Form Graph
    public static final String FORM_WITH_USER                       = "User:only";
    public static final String FORM_WITH_ENTRIES_WITH_FIELD_ENTRIES = "Form:entries+field_entries";
    public static final String FORM_WITH_FIELD_ENTRIES_FIELD        = "FormEntries:field";
    public static final String FIELD_CONFIG_WITH_FORM               = "FormConfig:form";

    // Field Graph
    public static final String FORM_FIELD_WITH_CONFIGS = "Field:configs";
    public static final String FORM_FIELD_FULL         = "Field:children+parents+configs+attributes+options";

    // Field-Config Graph
    public static final String FORM_FIELD_CONFIG_WITH_FIELD = "FieldConfig:field";

    private EntityGraphConstants() {
    }

}
