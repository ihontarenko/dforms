package df.base;

public interface Messages {

    // Database Error Messages
    String REQUIRED_ID_CANNOT_BE_NULL = "Primary ID must be non-null";
    String ERROR_USER_NOT_FOUND       = "User '%s' not found";
    String ERROR_FORM_NOT_FOUND       = "Form '%s' not found";
    String ERROR_FIELD_NOT_FOUND      = "Field '%s' not found";
    String ERROR_PRIVILEGE_NOT_FOUND  = "Privilege '%s' not found";
    String ERROR_ROLE_NOT_FOUND       = "Role '%s' not found";
    String FORM_CONFIG_NOT_FOUND      = "Configuration '%s' not found";
    String FORM_FIELD_NOT_FOUND       = "Form-Field '%s' not found";
    String ENTITY_NOT_FOUND           = "Entity with primary key '%s' not found";

    // Services Errors
    String ERROR_VIEW_NAME_MUST_BE_SPECIFIED       = "View name must be specified";
    String ERROR_REDIRECT_URL_MUST_BE_SPECIFIED    = "Redirect URL must be specified";
    String ERROR_REDIRECT_ATTRIBUTES_REQUIRED      = "'RedirectAttributes' required before adding a message";
    String ERROR_BINDING_RESULT_IS_REQUIRED        = "'BindingResult' is required in the context of the current request";
    String ERROR_FLASH_MESSAGE_SERVICE_IS_REQUIRED = "'FlashMessageService' is required";

    // Mapper Errors
    String ERROR_MAPPER_PASSWORD_ENCODER_IS_REQUIRED = "'PasswordEncoder' is required";

    // Success Messages
    // User, Role, Privilege
    String SUCCESS_USER_SAVED      = "User '%s' successfully saved";
    String SUCCESS_ROLE_SAVED      = "Role '%s' successfully saved";
    String SUCCESS_PRIVILEGE_SAVED = "Privilege '%s' successfully saved";

    // Form, Field, etc.
    String SUCCESS_FORM_SAVED           = "Form '%s' successfully saved";
    String SUCCESS_FORM_STATUS_CHANGED  = "Form status '%s' successfully updated to '%s'";
    String SUCCESS_FORM_DELETED         = "Form '%s' successfully deleted";
    String SUCCESS_FIELD_SAVED          = "Field '%s' successfully saved";
    String SUCCESS_FIELD_STATUS_CHANGED = "Field status '%s' successfully updated to '%s'";
    String SUCCESS_FIELD_DELETED        = "Field '%s' successfully permanently deleted!";
    String SUCCESS_CONFIG_SAVED         = "Configuration '%s' successfully saved";
    String SUCCESS_CONFIG_DELETED       = "Configuration '%s' successfully permanently deleted!";

}
