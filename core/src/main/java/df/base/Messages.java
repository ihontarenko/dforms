package df.base;

public interface Messages {

    // Error Messages
    String ERROR_ACCESS_DENIED_NOT_OWNER             = "You do not have access to edit this from because you are not owner";
    String ERROR_USER_NOT_FOUND                      = "User '%s' not found";
    String ERROR_FORM_NOT_FOUND                      = "Form '%s' not found";
    String ERROR_PRIVILEGE_NOT_FOUND                 = "Privilege '%s' not found";
    String ERROR_ROLE_NOT_FOUND                      = "Role '%s' not found";
    String ERROR_VIEW_NAME_MUST_BE_SPECIFIED         = "View name must be specified";
    String ERROR_REDIRECT_URL_MUST_BE_SPECIFIED      = "Redirect URL must be specified";
    String ERROR_REDIRECT_ATTRIBUTES_REQUIRED        = "'RedirectAttributes' required before adding a message";
    String ERROR_BINDING_RESULT_IS_REQUIRED          = "'BindingResult' is required in the context of the current request";
    String ERROR_FLASH_MESSAGE_SERVICE_IS_REQUIRED   = "'FlashMessageService' is required";
    // Mapper Errors
    String ERROR_MAPPER_PASSWORD_ENCODER_IS_REQUIRED = "'PasswordEncoder' is required";

    // Success Messages
    String SUCCESS_USER_SAVED = "User '%s' successfully saved";
    String SUCCESS_ROLE_SAVED = "Role '%s' successfully saved";

}
